# Sistema Distribuído em Java: Controle de Alunos e Votação Eletrônica
##  Arquitetura e Implementação por Questão

### Questão 1: Modelagem e Serviços (POJOs)
* **Implementação:**  Foram criadas classes Java tradicionais (POJOs) com herança (`Aluno`, `AlunoGraduacao`, `AlunoIC`, `AlunoPosGraduacao`).
* **Contrato:** A interface `Remuneravel` implementa o cálculo de pagamento com base nos dias trabalhados e valor da bolsa. Já a interface de serviço remoto (`ServicoControleAlunos`) dita as ações do servidor.

### Questão 2: Empacotamento Manual (Custom OutputStream)
* **Implementação:** Criação da classe `AlunoOutputStream` estendendo `OutputStream`.
* **Fluxo:** Essa classe recebe um array de objetos. Ela informa na rede o número total de objetos e, para cada aluno, utiliza um buffer em memória (`ByteArrayOutputStream`) para converter ID, Nome e Matrícula em bytes. Em seguida, ela calcula o tamanho desse bloco, envia o tamanho e depois os dados reais.

### Questão 3: Desempacotamento Manual (Custom InputStream)
* **Implementação:** Criação da classe `AlunoInputStream` estendendo `InputStream`.
* **Fluxo:** Lê o int inicial (quantidade de objetos). Dentro de um loop for, lê o tamanho em bytes do próximo objeto, aloca um buffer exato (`byte[] buffer = new byte[numBytes]`), lê os dados brutos e extrai as variáveis reconstruindo a instância do objeto `Aluno`.

### Questão 4: Cliente-Servidor TCP (Unicast)
* **Implementação:** Utilização de `ServerSocket` e `Socket` na porta `9999`.
* **Fluxo:** O Cliente instancia um Aluno, utiliza o `AlunoOutputStream` para empacotar e enviar os bytes, e aguarda resposta. O Servidor recebe a conexão, desempacota com `AlunoInputStream`, processa a requisição (alterando o nome para confirmar) e devolve a resposta empacotada.

### Questão 5: Sistema de Votação (Multicast e JSON)
* **Representação de Dados:** Utilização da biblioteca **Gson** para serializar DTOs (`RequisicaoVoto`, `RespostaServidor`, `NotaInformativa`) em formato JSON.
* **Votação via TCP (Unicast):** Para garantir a entrega do voto, o cliente abre um `Socket` na porta `8080`. O servidor é *Multi-threaded*, criando uma nova `Thread (ClientHandler)` para cada eleitor, suportando múltiplos acessos simultâneos sem gargalos.
* **Notificações via UDP (Multicast):** Para otimizar a rede ao avisar centenas de eleitores sobre o tempo restante, o servidor dispara um único pacote UDP via `MulticastSocket` na porta `4446` para o IP de grupo `230.0.0.0`. Todos os clientes possuem uma Thread em background escutando essa frequência.
* **Controle de Tempo:** O servidor inicia uma `Thread` que controla o tempo de eleição (ex: 1 minuto). Após o prazo, a flag de controle é fechada e novos votos TCP são rejeitados com um status de erro em JSON, disparando os resultados finais via Multicast.