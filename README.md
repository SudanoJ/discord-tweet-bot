# discord-tweet-bot
##### (ou PitsBot!)
🐦| Um bot de discord que permite publicar tweet's utilizando comandos!

O discord-tweet-bot é um bot de discord feito em Java que utiliza [JDA](https://github.com/DV8FromTheWorld/JDA) (Java Discord API) para o bot e [Twittered](https://github.com/redouane59/twittered) para enviar os Tweet's.

#### p.s: Nenhum TOKEN que está no código está funcionando.

![Obrigado kp por me ajudar a pensar!](https://i.imgur.com/LwuZrgk.png)

## Instalação

O primeiro passo para instalar e funcionar corretamente é colocando o Token do seu bot de discord, que pode ser encontrado na aba "bots" [da sua aplicação](https://discord.com/developers/applications) do Discord.

```java
public static final String TOKEN = "INSERT_YOUR_TOKEN";
```

Agora, para conectar no Twitter você deve alterar o arquivo ``config.json`` e colocar as chaves corretas de acordo com sua aplicação no twitter.

_Você pode encontrar suas credenciais [na página das aplicações do twitter](https://developer.twitter.com/en/apps)_.

```json
{
  "apiKey": "xxx",
  "apiSecretKey": "xxx",
  "accessToken": "xxx",
  "accessTokenSecret": "xxx"
}
```

Por fim, você deve alterar o link do WEBHOOK da classe ``TweetCommand.java`` para receber o registro dos tweet's feitos.
```java
public final static String WEBHOOK_URL = "https://discord.com/api/webhooks/WEBHOOK_CODE";
``` 

## Modo de uso:
Após instalado e com tudo funcionando corretamente, você pode utilizar qualquer um dos **3** comandos feitos, sendo eles:

| Comando   | O que faz? |      Permissão necessária      | Cooldown |
|----------|:-------------:|:-------------:|---|
| ``p!tweet <mensagem>`` | Posta um tweet.                         | Nenhuma | 60s |
| ``p!config <args>``    | Altera as palavras bloqueadas.          | Nenhuma | 0s |
| ``p!refresh``          | Atualiza a lista ``blocked_words.json`` | Nenhuma | 0s |

## Todo List:
- [ ] Suporte para tweet com imagens.
- [ ] Corrigir o sistema de cooldown.
