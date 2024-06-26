# Один поток - один запрос
![thread-per-request.png](art%2Fthread-per-request.png)
# Event Loop
![event-loop.jpg](art%2Fevent-loop.jpg)

Реактивное программирование в Spring основывается на Project Reactor https://projectreactor.io/ - фреймворке, реализующем спецификацию Reactive Streams https://www.reactive-streams.org/.

Реактивные стримы можно представить в виде продъюсеров и консьюмеров данных. Продъюсеры 
(Publishers) являются источниками данных. Основные типы продъюсеров в Project Reactor:

- `Mono<T>` - представляет 0..1 значение типа T или ошибку
- `Flux<T>` - представляет 0..N значений типа T или ошибку

Консьюмеры (Subscribers) подписываются на продъюсеров и получают уведомления о поступлении
новых данных. Также консьюмеры могут уведомлять продъюсеров о том, что тем необходимо сбавить
темп предоставления данных, так как консьюмер не справляется с их обработкой - это называется
backpressure.

https://habr.com/ru/articles/565000/ - статьи про реактивное программирование со 
Spring WebFlux.

## Offset Keyset пагинация
https://www.youtube.com/watch?v=wi6h9ox1wwM