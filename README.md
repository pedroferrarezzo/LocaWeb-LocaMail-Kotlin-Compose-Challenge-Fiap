# DESAFIO

"Desenvolver um aplicativo de e-mail, inovador, que visa revolucionar a forma como as pessoas interagem com suas caixas de entrada.

O objetivo é criar uma aplicação que seja intuitiva, eficiente e altamente personalizável, oferecendo recursos avançados de organização, segurança e integração com outras plataformas.

Requisitos:
- Interface Intuitiva e Atraente: O aplicativo deve ter uma interface de usuário limpa, moderna e fácil de usar. A navegação entre as funcionalidades deve ser intuitiva, permitindo que os usuários acessem rapidamente suas mensagens e gerenciem suas caixas de entrada;
- Recursos Avançados de Organização: Implemente recursos avançados de organização de e-mails, como marcadores, filtros inteligentes, categorização automática e destaque de mensagens importantes;
- Integração com calendário: Permitir a integração do aplicativo de e-mail com calendário. Isso proporcionará uma experiência mais integrada e conveniente para os usuários;
- Modo Offline: Arquitete uma forma para o funcionamento do aplicativo de modo offline, que permita aos usuários acessar e gerenciar seus e-mails e calendário mesmo quando não estiverem conectados à internet."

# TECNOLOGIAS UTILIZADAS
- Kotlin;
- Android API Level 27 (Android Oreo);
- Framework UI: Jetpack Compose;
- SGBD SQLite;
- ORM ROOM;
- Retrofit e GSON;
- Google Gemini LLM.

# HANDS ON - FLUXO DE ENVIO E RESPOSTA DE EMAIL
https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/426a9b43-e7d5-473b-88af-3a26a69019e1

# HANDS ON - INTEGRAÇÃO CALENDÁRIO X EMAIL
https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/f18d1e4e-16a6-47c5-b929-947ef5a86254

# CARACTERÍSTICAS DISTINTIVAS DO APLICATIVO
- Multicontas: Permite criar e gerenciar múltiplas contas de email em um único aplicativo, facilitando a organização de diferentes perfis de usuário;
- Sincronização Offline: Todos os emails e compromissos podem ser acessados e gerenciados sem conexão com a internet;
- Gerenciamento de Rascunhos: Emails não enviados são automaticamente salvos como rascunhos, garantindo que nenhuma mensagem importante seja perdida;
- Organização Avançada: Marque emails como favoritos, spam, arquivados ou excluídos. Crie pastas personalizadas para organizar suas mensagens de maneira eficiente;
- Calendário Integrado: Criação de tarefas e eventos diretamente no aplicativo. Ao convidar participantes, os convites são enviados por email e, quando aceitos, os eventos são automaticamente adicionados ao calendário dos convidados;
- Classificação Automática: Convites de eventos são automaticamente categorizados como emails sociais, facilitando a organização da caixa de entrada;
- Integração com Google API: Utilize a integração com a API do Google para elaborar perguntas sobre emails enviados ou recebidos, aumentando a produtividade e a eficiência no gerenciamento de informações.

# MODELO DE ENTIDADE E RELACIONAMENTO
![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/cad1d717-8e33-4255-a7da-115a2f5e484d)

# BOAS PRÁTICAS
- Usabilidade:
  - <p>Seções como “spam” e “lixeira” contam com uma botão de usabilidade “limpar tudo”, permitindo movimentar todos os emails daquela seção para lixeira ou excluí-los definitivamente;</p>
  - <p>Todas as telas possuem no canto superior um componente que permite filtrar os emails exibidos por meio de uma barra de pesquisa, acessar o menu de opções ou trocar rapidamente de usuário;</p>
  - <p>Todos os atributos contentDescription dos componentes de imagens e ícones foram preenchidos para viabilizar a leitura de softwares de acessibilidade;</p>
  - <p>Todas as strings e contentDescriptions foram preenchidas utilizando o arquivos strings.xml. Possibilitando assim a tradução automática de todo o aplicativo para a lingua ingluesa;</p>
  
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/228fd7f4-76a6-4b72-8405-8e42964644a7)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/8e54ffae-1656-4dac-b726-a1baf45b5b6d)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/baadae59-7dd9-4fda-a364-89c9192ff01a)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/ef8e5fc4-20b1-47cd-9047-37539e4686cd)
   
    
  - <p>Em vários momentos do aplicativo, Toast Messages são exibidas para que o usuário fique ciente de que o que ele estava tentando fazer funcionou. Elas foram construídas utilizando o arquivo strings.xml, viabilizando sua tradução.</p>
  
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/ac60b2d4-f5e4-4aaa-a054-4afc58ef94ef)

  - <p>Em vários momentos do aplicativo mensagens são exibidas em resposta ao mal ou não preenchimento de campos - neste caso, obrigatórios;</p>
  - <p>Quando uma senha é inserida no formato incorreto, uma mensagem de erro é mostrada e a visualização da senha é automaticamente habilitada;</p>
  - <p>O tema de modo “Dark” foi implementado no aplicativo para melhor usabilidade;</p>
  - <p>A hora é mostrada de acordo com o formato de hora configurado no celular (AM/PM ou 24 horas);</p>
  - <p>Funcionalidades que precisam de acesso á internet retornam telas customizadas em caso de erro.</p>
  
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/c1495cdb-425b-4827-9577-26407d084993)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/06d67090-92fc-453f-a26b-90d2cc0d24c2)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/afba7208-4ffd-411b-8c49-1c5885a9890a)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/a2b97010-7bd6-4d63-b5db-f40f0ddd5c55)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/2a6483a0-cf01-4ed0-94a9-4a8b46932a4f)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/7e603a9b-bbbe-4c75-adcf-702677bddb41)
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/226365cc-20d3-4ddc-8318-9ee0803e9d9a)
  
    


- Segurança:
  - As senhas dos usuários quando armazenadas no banco de dados não são salvas em string pura, mas sim o seu hash utilizando sha256;
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/67b1d41e-6dcc-4ae9-9b76-c44d6705b92f)

  - Dados sensíveis como Tokens de API não foram inseridos diretamente no código fonte da aplicação, mas em um arquivo de propriedades a parte.
    ![image](https://github.com/pedroferrarezzo/locawebmailapp-fiap/assets/124400471/6b1e79ba-4a5e-4582-958b-50e41bc020ec)
