# Front-End Cadastro de Empresas (Swing)

Sistema Desktop (Swing) que realiza cadastro de empresas consumindo uma API própria e a API de localização do IBGE, utilizando banco de dados h2 e arquivos de Log (TXT)
Tecnologias utilizadas: Java 8, NetBeans, Biblioteca Swing e Retrofit
Para a execução do sistema é necessário informar o endereço IP do computador que está executando a API de forma local. Esse IP deve ser informado através de uma alteração no método: BaseURL, que se encontra no pacote: cadastroempresas.retrofit.service_retrofit, e na classe Retrofit_URL
