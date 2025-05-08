const mineflayer = require('mineflayer');

function createBot(host, port, version) {
  return new Promise((resolve) => {
    const bot = mineflayer.createBot({
      host: host,
      port: parseInt(port),
      version: version,
      username: 'Bot_' + Math.random().toString(36).substring(7),
    });

    let kickedReason = null;
    let timeoutId = null;
    bot.on('kicked', (reason) => {
      kickedReason = reason;
      bot.end();
      let kickMessage;
      if (typeof reason === 'object' && reason.text) {
        kickMessage = reason.text;
      } else if (typeof reason === 'string') {
        kickMessage = reason;
      } else {
        kickMessage = JSON.stringify(reason);
      }
      resolve(`Kicked: ${kickMessage}`);
    });

    bot.on('login', () => {
      timeoutId = setTimeout(() => {
        if (!kickedReason) {
          bot.quit();
          resolve('Kicked: Bot not kicked for 5 seconds.');
        }
      }, 5000);
    });

    bot.on('error', (err) => {
      resolve(`Connection error: ${err.message}`);
    });

    bot.on('end', () => {
      if (!kickedReason) {
        resolve('Kicked: Bot not kicked for 5 seconds.');
      }
    });
  });
}

const [,, host, port, version] = process.argv;
if (!host || !port || !version) {
  console.error('<host> <port> <version>');
  process.exit(1);
}

createBot(host, port, version)
  .then((result) => {
    console.log(result);
    process.exit(0);
  })
  .catch((err) => {
    console.error(err);
    process.exit(1);
  });