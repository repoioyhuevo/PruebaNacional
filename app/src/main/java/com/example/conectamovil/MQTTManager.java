    package com.example.conectamovil;

    import android.content.Context;
    import org.eclipse.paho.android.service.MqttAndroidClient;
    import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
    import org.eclipse.paho.client.mqttv3.IMqttToken;
    import org.eclipse.paho.client.mqttv3.MqttCallback;
    import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
    import org.eclipse.paho.client.mqttv3.MqttMessage;

    import java.util.Timer;
    import java.util.TimerTask;

    public class MQTTManager {

        private MqttAndroidClient mqttAndroidClient;
        private Timer pingTimer;

        public MQTTManager(Context context) {
            String brokerUri = "tcp://localhost:1883";
            String clientId = "root";
            mqttAndroidClient = new MqttAndroidClient(context, brokerUri, clientId);

            // Inicializar el temporizador para enviar pings periódicos
            initPingTimer();

            connectToBroker();
            setCallback();
        }

        private void initPingTimer() {
            pingTimer = new Timer();
            pingTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sendPing();
                }
            }, 0, 60000); // Envía un ping cada 60 segundos (ajusta según sea necesario)
        }

        private void sendPing() {
            // Implementa aquí el código para enviar un ping al servidor MQTT
            // Puedes usar mqttAndroidClient.ping() o enviar un mensaje vacío al servidor
        }

        private void connectToBroker() {
            try {
                MqttConnectOptions options = new MqttConnectOptions();
                options.setAutomaticReconnect(true);
                options.setCleanSession(false);

                IMqttToken token = mqttAndroidClient.connect(options, null, null);
                token.waitForCompletion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setCallback() {
            mqttAndroidClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // Manejar la pérdida de conexión
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // Manejar los mensajes entrantes
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Manejar la entrega completa del mensaje (opcional)
                }
            });
        }

        public void subscribeToTopic(String topic) {
            try {
                IMqttToken subToken = mqttAndroidClient.subscribe(topic, 1);
                subToken.waitForCompletion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void publishMessage(String topic, String message) {
            try {
                mqttAndroidClient.publish(topic, message.getBytes(), 1, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
