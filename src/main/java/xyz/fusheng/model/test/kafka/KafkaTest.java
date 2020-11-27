package xyz.fusheng.model.test.kafka;

/**
 * @FileName: KafkaTest
 * @Author: code-fusheng
 * @Date: 2020/11/19 13:41
 * @version: 1.0
 * Description: kafka 测试类
 */

// @RestController
// @RequestMapping("kafka")
// @Slf4j
// public class KafkaTest {
//
//     @Resource
//     private KafkaTemplate<Object, Object> kafkaTemplate;
//
//     @GetMapping("/send/{message}")
//     public void sendFoo(@PathVariable String message) {
//         this.kafkaTemplate.send("test", message);
//     }
//
//     @KafkaListener(id = "webGroup", topics = "test")
//     public void listen(String message) {
//         log.info("message value: {}", message);
//     }
//
// }
