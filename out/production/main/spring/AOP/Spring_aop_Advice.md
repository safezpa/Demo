Spring AOP 即 Aspect-oriented programming，面向切面编程，是作为面向对象编程的一种补充，专门用于处理系统中分布于各个模块（不同方法）中的交叉关注点的问题。简单地说，就是一个拦截器（ interceptor ）拦截一些处理过程。

例如，当一 个method 被执行，Spring AOP 能够劫持正在运行的 method ，在 method 执行前或者后加入一些额外的功能。

在 Spring AOP 中，支持 4 种类型的通知（ Advice ）：

    Before advice - method 执行前通知
    After returning advice - method 返回一个结果后通知
    After throwing advice - method 抛出异常后通知
    Around advice - 环绕通知，结合了以上三种

这样就引入了 Pointcut （切入点）的概念，
它允许你根据 method 的名字去拦截指定的 method 。
另外，一个 Pointcut 必须结合一个 Advisor 来使用。
本次实验将学习 Pointcut 的相关知识点。

在 Spring AOP 中，有 3 个常用的概念，Advices 、 Pointcut 、 Advisor ，解释如下：

    Advices ：表示一个 method 执行前或执行后的动作。
    Pointcut ：表示根据 method 的名字或者正则表达式去拦截一个 method 。
    Advisor ： Advice 和 Pointcut 组成的独立的单元，并且能够传给 proxy factory 对象。

我们可以用名字匹配法和正则表达式匹配法去匹配要拦截的 method 。