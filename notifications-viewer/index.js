import {connect} from 'amqplib';

let url = {
    protocol: 'amqp',
    hostname: 'notifications_rabbit',
    port: 5672,
    username: 'admin',
    password: 'admin',
    locale: 'en_US',
    frameMax: 0,
    heartbeat: 0,
    vhost: '/',
}

const connection = await connect(url);
const channel = await connection.createChannel();
const queue = 'notification_queue';
await channel.assertQueue(queue, {durable: true});

channel.consume(queue, msg => {
    const msg_content = JSON.parse(msg.content.toString());
    const date = msg_content['date'];
    const message = msg_content['message'];

    console.log(`[${date}] ${message}`);
    channel.ack(msg);
});
