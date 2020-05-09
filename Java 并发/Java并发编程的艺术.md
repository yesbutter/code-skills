目录

* [Java并发编程的艺术](#java并发编程的艺术)
  * [1  并发编程的挑战](#1--并发编程的挑战)
    * [1.1  上下文切换](#11--上下文切换)
    * [1.2  死锁](#12--死锁)
    * [1.3  资源的挑战](#13--资源的挑战)
  * [2  Java并发与底层实现原理](#2--java并发与底层实现原理)
    * [2.1  volatile](#21--volatile)
    * [2.2  synchronized](#22--synchronized)
    * [2.3  原子操作](#23--原子操作)
  * [3  Java内存模型](#3--java内存模型)
    * [3.1  Java内存模型的基础](#31--java内存模型的基础)
    * [3.2  happens-before](#32--happens-before)
    * [3.3  实例](#33--实例)
  * [4  线程](#4--线程)
    * [4.1  为什么使用多线程](#41--为什么使用多线程)
    * [4.2  线程间通信](#42--线程间通信)
    * [4.3  线程应用](#43--线程应用)
  * [5  锁](#5--锁)
    * [5.1  Lock接口](#51--lock接口)
    * [5.2  队列同步器(AQS)](#52--队列同步器aqs)
    * [5.3  重入锁](#53--重入锁)
    * [5.4  读写锁](#54--读写锁)
    * [5.5  LockSupport](#55--locksupport)
    * [5.6  Condition接口](#56--condition接口)
  * [6  并发容器和框架](#6--并发容器和框架)
    * [6.1  ConcurrentHashMap](#61--concurrenthashmap)
    * [6.2  ConcurrentLinkedQueue](#62--concurrentlinkedqueue)
    * [6.3  阻塞队列](#63--阻塞队列)
    * [6.4  Fork/Join框架](#64--forkjoin框架)
  * [7  Java的原子操作类](#7--java的原子操作类)
    * [7.1  原子更新基本类型类](#71--原子更新基本类型类)
    * [7.2  原子更新数组](#72--原子更新数组)
    * [7.3  原子更新引用类型](#73--原子更新引用类型)
    * [7.4  原子更新字段类](#74--原子更新字段类)
  * [8  Java中的并发工具类](#8--java中的并发工具类)
    * [8.1  CountDownLatch](#81--countdownlatch)
    * [8.2  CyclicBarrier](#82--cyclicbarrier)
    * [8.3  Semaphore](#83--semaphore)
    * [8.4  Exchanger](#84--exchanger)
  * [9  Java 线程池](#9--java-线程池)
    * [9.1  实现原理](#91--实现原理)
    * [9.2  线程池的创建参数](#92--线程池的创建参数)
    * [9.3 向线程池提交任务](#93-向线程池提交任务)
    * [9.4  关闭线程池](#94--关闭线程池)
    * [9.5  合理地配置线程池](#95--合理地配置线程池)
    * [9.6  线程池的监控](#96--线程池的监控)
  * [10  Executor](#10--executor)

## 推荐阅读书籍 
《Java并发编程的艺术》[提取码ourf](https://pan.baidu.com/s/1GmblPSUhFFtLVoDIiOp33Q)## xmind在同级目录下，需要自取。

# Java并发编程的艺术

## 1  并发编程的挑战

### 1.1  上下文切换

### 1.2  死锁

### 1.3  资源的挑战

资源限制是指在进行并发编程时，程序的执行速度受限于计算机硬件资源或软件资源。 例如，服务器的带宽只有2Mb/s，某个资源的下载速度是1Mb/s每秒，系统启动10个线程下载资 源，下载速度不会变成10Mb/s，所以在进行并发编程时，要考虑这些资源的限制。硬件资源限 制有带宽的上传/下载速度、硬盘读写速度和CPU的处理速度

## 2  Java并发与底层实现原理

### 2.1  volatile

- 2.1.1  实现原理

  有volatile变量修饰的共享变量进行写操作的时候会多出第二行汇编代码
  1）将当前处理器缓存行的数据写回到系统内存。
   2）这个写回内存的操作会使在其他CPU里缓存了该内存地址的数据无效。

- 2.1.2  使用优化

  Java并发编程大师Doug lea在JDK 7的并发包里新增一个队列集合类LinkedTransferQueue，它在使用volatile变量时，用一种追加字节的方式来优化队列出队和入队的性 能。LinkedTransferQueue的代码如下。
   /** 队列中的头部节点 */ 
  private transient final PaddedAtomicReference head; 
  /** 队列中的尾部节点 */ 
  private transient final PaddedAtomicReference tail; 
  static final class PaddedAtomicReference  extends AtomicReference T> 
  { 
  
  ​	// 使用很多4个字节的引用追加到64个字节 Object p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, pa, pb, pc, pd, pe; 
  
  ​	PaddedAtomicReference(T r) 
  ​	{ 
  
  ​		super(r); 
  
  ​	} 
  
  }  
  LinkedTransferQueue这个类，它使用一个内部类类型来定义队列的 头节点（head）和尾节点（tail），而这个内部类PaddedAtomicReference相对于父类 AtomicReference只做了一件事情，就是将共享变量追加到64字节。我们可以来计算下，一个对 象的引用占4个字节，它追加了15个变量（共占60个字节），再加上父类的value变量，一共64个 字节。 为什么追加64字节能够提高并发编程的效率呢？因为对于英特尔酷睿i7、酷睿、Atom和 NetBurst，以及Core Solo和Pentium M处理器的L1、L2或L3缓存的高速缓存行是64个字节宽，不 支持部分填充缓存行，这意味着，如果队列的头节点和尾节点都不足64字节的话，处理器会将 它们都读到同一个高速缓存行中，在多处理器下每个处理器都会缓存同样的头、尾节点，当一 个处理器试图修改头节点时，会将整个缓存行锁定，那么在缓存一致性机制的作用下，会导致 其他处理器不能访问自己高速缓存中的尾节点，而队列的入队和出队操作则需要不停修改头 节点和尾节点
  所以在多处理器的情况下将会严重影响到队列的入队和出队效率。Doug lea使 用追加到64字节的方式来填满高速缓冲区的缓存行，避免头节点和尾节点加载到同一个缓存 行，使头、尾节点在修改时不会互相锁定。

### 2.2  synchronized

·对于普通同步方法，锁是当前实例对象。 ·对于静态同步方法，锁是当前类的Class对象。 ·
对于同步方法块，锁是Synchonized括号里配置的对象。

- 2.2.1   Java对象头

  synchronized用的锁是存在Java对象头里的。

- 2.2.2  锁的升级与对比

  Java SE 1.6为了减少获得锁和释放锁带来的性能消耗，引入了“偏向锁”和“轻量级锁”，在 Java SE 1.6中，锁一共有4种状态，级别从低到高依次是：无锁状态、偏向锁状态、轻量级锁状 态和重量级锁状态，这几个状态会随着竞争情况逐渐升级。锁可以升级但不能降级，意味着偏 向锁升级成轻量级锁后不能降级成偏向锁。

	- 偏向锁

	  HotSpot的作者经过研究发现，大多数情况下，锁不仅不存在多线程竞争，而且总是由同 一线程多次获得，为了让线程获得锁的代价更低而引入了偏向锁。当一个线程访问同步块并 获取锁时，会在对象头和栈帧中的锁记录里存储锁偏向的线程ID，以后该线程在进入和退出 同步块时不需要进行CAS操作来加锁和解锁，只需简单地测试一下对象头的Mark Word里是否 存储着指向当前线程的偏向锁。如果测试成功，表示线程已经获得了锁。如果测试失败，则需 要再测试一下Mark Word中偏向锁的标识是否设置成1（表示当前是偏向锁）：如果没有设置，则 使用CAS竞争锁；如果设置了，则尝试使用CAS将对象头的偏向锁指向当前线程。

	- 轻量级锁

	  线程在执行同步块之前，JVM会先在当前线程的栈桢中创建用于存储锁记录的空间，并 将对象头中的Mark Word复制到锁记录中，官方称为Displaced Mark Word。然后线程尝试使用 CAS将对象头中的Mark Word替换为指向锁记录的指针。如果成功，当前线程获得锁，如果失 败，表示其他线程竞争锁，当前线程便尝试使用自旋来获取锁。

	- 重量级锁

	  线程竞争不使用自旋，不会消耗CPU，线程阻塞相应时间慢。
	  适用于追求吞吐量，同步执行代码速度较长。

### 2.3  原子操作

原子（atomic）本意是“不能被进一步分割的最小粒子”，而原子操作（atomic operation）意 为“不可被中断的一个或一系列操作”。

- 2.3.1  如何实现原子操作

	- 总线锁

	  总线锁就是使用处理器提供的一个 LOCK＃信号，当一个处理器在总线上输出此信号时，其他处理器的请求将被阻塞住，那么该 处理器可以独占共享内存。

	- 缓存锁

	  在同一时刻，我们只需保证对某个内存地址 的操作是原子性即可，但总线锁定把CPU和内存之间的通信锁住了，这使得锁定期间，其他处 理器不能操作其他内存地址的数据。
	  “缓存锁定”是指内存区域如果被缓存在处理器的缓存行中，并且在Lock操作期间被锁定，那么当它执行锁操作回写到内存时，处理器不在总线上声 言LOCK＃信号，而是修改内部的内存地址，并允许它的缓存一致性机制来保证操作的原子 性，因为缓存一致性机制会阻止同时修改由两个以上处理器缓存的内存区域数据，当其他处 理器回写已被锁定的缓存行的数据时，会使缓存行无效

- 2.3.2  Java如何实现原子操作

	- 循环CAS

	  JVM中的CAS操作正是利用了处理器提供的CMPXCHG指令实现的。自旋CAS实现的基本 思路就是循环进行CAS操作直到成功为止。

		- ABA问题

		  因为CAS需要在操作值的时候，检查值有没有发生变化，如果没有发生变化 则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它 的值没有发生变化，但是实际上却变化了。
		  解决方法：使用版本号。在变量前面 追加上版本号，每次变量更新的时候把版本号加1，那么A→B→A就会变成1A→2B→3A。

		- 循环时间长开销大

		  自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销。如 果JVM能支持处理器提供的pause指令，那么效率会有一定的提升。pause指令有两个作用：第 一，它可以延迟流水线执行指令（de-pipeline），使CPU不会消耗过多的执行资源，延迟的时间 取决于具体实现的版本，在一些处理器上延迟时间是零；第二，它可以避免在退出循环的时候 因内存顺序冲突（Memory Order Violation）而引起CPU流水线被清空（CPU Pipeline Flush），从而 提高CPU的执行效率。

		- 只能保证一个共享变量的原子操作。

		  解决方法：把多个共享变量合并成一个共享变量来 操作。比如，有两个共享变量i＝2，j=a，合并一下ij=2a，然后用CAS来操作ij。从Java 1.5开始， JDK提供了AtomicReference类来保证引用对象之间的原子性，就可以把多个变量放在一个对 象里来进行CAS操作。

## 3  Java内存模型

### 3.1  Java内存模型的基础

- 3.1.1  并发编程模型线程通信

	- 共享内存

	  线程之间共享程序的公共状态，通过写-读内存中的公共状态 进行隐式通信。
	  Java的并发采用的是共享内存模型

	- 消息传递

	  线程之间没有公共状态，线程之间必须通过发送消 息来显式进行通信。

- 3.1.2  Java内存模型抽象结构

  在Java中，所有实例域、静态域和数组元素都存储在堆内存中，堆内存在线程之间共享。局部变量（Local Variables），方 法定义参数（Java语言规范称之为Formal Method Parameters）和异常处理器参数（Exception Handler Parameters）不会在线程之间共享，它们不会有内存可见性问题，也不受内存模型的影 响。

	- 共享内存

	  1）线程A把本地内存A中更新过的共享变量刷新到主内存中去。
	   2）线程B到主内存中去读取线程A之前已更新过的共享变量。
	  从整体来看，这两个步骤实质上是线程A在向线程B发送消息，而且这个通信过程必须要 经过主内存。JMM通过控制主内存与每个线程的本地内存之间的交互，来为Java程序员提供 内存可见性保证。

- 3.1.3  指令重排

	- 编译器优化的重排序

	  编译器在不改变单线程程序语义的前提下，可以重新安排语句 的执行顺序。

	- 指令级并行的重排序

	  现代处理器采用了指令级并行技术（Instruction-Level Parallelism，ILP）来将多条指令重叠执行。如果不存在数据依赖性，处理器可以改变语句对应 机器指令的执行顺序。

	- 内存系统的重排序

	  由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上 去可能是在乱序执行。

### 3.2  happens-before

在JMM中，如果一 个操作执行的结果需要对另一个操作可见，那么这两个操作之间必须要存在happens-before关 系。这里提到的两个操作既可以是在一个线程之内，也可以是在不同线程之间

- 程序顺序规则

  一个线程中的每个操作，happens-before于该线程中的任意后续操作。

- 监视器锁规则

  对一个锁的解锁，happens-before于随后对这个锁的加锁。

- volatile变量规则

  ：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。

- start()规则

  如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的 ThreadB.start()操作happens-before于线程B中的任意操作。

- join()规则

  如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作 happens-before于线程A从ThreadB.join()操作成功返回。

- 传递性

  如果A happens-before B，且B happens-before C，那么A happens-before C

### 3.3  实例

- 3.3.1 DCL

- 3.3.2  延迟初始化

  public class InstanceFactory 
  { 
  	private static class InstanceHolder 
  	{ 
  		public static Instance instance = new Instance(); 
  	} 
  	public static Instance getInstance() 
  
  ​	{ 
  
  ​		return InstanceHolder.instance ; // 这里将导致InstanceHolder类被	初始化 
  ​	}
  }

## 4  线程

### 4.1  为什么使用多线程

正确使用多线程，总是能够给开发人员带来显著的好处，而使用多线 程的原因主要有以下几点。

- 更多的处理器核心

  随着处理器上的核心数量越来越多，以及超线程技术的广泛运用，现在大多数计算机都 比以往更加擅长并行计算，而处理器性能的提升方式，也从更高的主频向更多的核心发展。

- 更快的响应时间

  有时我们会编写一些较为复杂的代码（这里的复杂不是说复杂的算法，而是复杂的业务逻 辑）

- 更好的编程模型

  Java为多线程编程提供了良好、考究并且一致的编程模型，使开发人员能够更加专注于问 题的解决，即为所遇到的问题建立合适的模型，而不是绞尽脑汁地考虑如何将其多线程化。一 旦开发人员建立好了模型，稍做修改总是能够方便地映射到Java提供的多线程编程模型上。

### 4.2  线程间通信

- 4.2.1  共享内存

  Java支持多个线程同时访问一个对象或者对象的成员变量，由于每个线程可以拥有这个 变量的拷贝（虽然对象以及成员变量分配的内存是在共享内存中的，但是每个执行的线程还是 可以拥有一份拷贝，这样做的目的是加速程序的执行，这是现代多核处理器的一个显著特 性）

	- volatile

	  关键字volatile可以用来修饰字段（成员变量），就是告知程序任何对该变量的访问均需要 从共享内存中获取，而对它的改变必须同步刷新回共享内存，它能保证所有线程对变量访问 的可见性。

	- synchronized

	  关键字synchronized可以修饰方法或者以同步块的形式来进行使用，它主要确保多个线程 在同一个时刻，只能有一个线程处于方法或者同步块中，它保证了线程对变量访问的可见性 和排他性。

- 4.2.2  等待/通知机制

  一个线程修改了一个对象的值，而另一个线程感知到了变化，然后进行相应的操作，整个 过程开始于一个线程，而最终执行又是另一个线程。前者是生产者，后者就是消费者，这种模 式隔离了“做什么”（what）和“怎么做”（How）

	- wait/notify
	- 管道输入/输出流

### 4.3  线程应用

- 4.3.1  等待超时

	- 问题

	  开发人员经常会遇到这样的方法调用场景：调用一个方法时等待一段时间（一般来说是给 定一个时间段），如果该方法能够在给定的时间段之内得到结果，那么将结果立刻返回，反之， 超时返回默认结果。

	- 实现

	  这时仅需要wait(REMAINING)即可，在wait(REMAINING)返回之后会将执行： REMAINING=FUTURE–now。如果REMAINING小于等于0，表示已经超时，直接退出，否则将 继续执行wait(REMAINING)。

- 4.3.2  线程池技术

  线程池的本质就是使用了一个线程安全的工作队列连接工作者线程和客户端 线程，客户端线程将任务放入工作队列后便返回，而工作者线程则不断地从工作队列上取出 工作并执行。当工作队列为空时，所有的工作者线程均等待在工作队列上，当有客户端提交了 一个任务之后会通知任意一个工作者线程，随着大量的任务被提交，更多的工作者线程会被 唤醒。

	- 问题

	  对于服务端的程序，经常面对的是客户端传入的短小（执行时间短、工作内容较为单一） 任务，需要服务端快速处理并返回结果。如果服务端每次接受到一个任务，创建一个线程，然 后进行执行，这在原型阶段是个不错的选择，但是面对成千上万的任务递交进服务器时，如果 还是采用一个任务一个线程的方式，那么将会创建数以万记的线程，这不是一个好的选择。因 为这会使操作系统频繁的进行线程上下文切换，无故增加系统的负载，而线程的创建和消亡 都是需要耗费系统资源的，也无疑浪费了系统资源。

	- 实现

	  线程池技术能够很好地解决这个问题，它预先创建了若干数量的线程，并且不能由用户 直接对线程的创建进行控制，在这个前提下重复使用固定或较为固定数目的线程来完成任务 的执行。这样做的好处是，一方面，消除了频繁创建和消亡线程的系统资源开销，另一方面， 面对过量任务的提交能够平缓的劣化。

## 5  锁

### 5.1  Lock接口

它提供了与synchronized关键字类似的同步功 能，只是在使用时需要显式地获取和释放锁。虽然它缺少了（通过synchronized块或者方法所提 供的）隐式获取释放锁的便捷性，但是却拥有了锁获取与释放的可操作性、可中断的获取锁以 及超时获取锁等多种synchronized关键字所不具备的同步特性。

### 5.2  队列同步器(AQS)

队列同步器AbstractQueuedSynchronizer（以下简称同步器），是用来构建锁或者其他同步组 件的基础框架，它使用了一个int成员变量表示同步状态，通过内置的FIFO队列来完成资源获 取线程的排队工作.

同步器面向的是锁的实现者， 它简化了锁的实现方式，屏蔽了同步状态管理、线程的排队、等待与唤醒等底层操作。锁和同 步器很好地隔离了使用者和实现者所需关注的领域。

- 5.2.1  同步队列

  同步器依赖内部的同步队列（一个FIFO双向队列）来完成同步状态的管理，当前线程获取 同步状态失败时，同步器会将当前线程以及等待状态等信息构造成为一个节点（Node）并将其 加入同步队列，同时会阻塞当前线程，当同步状态释放时，会把首节点中的线程唤醒，使其再 次尝试获取同步状态。 
  同步器包含了两个节点类型的引用，一个指向头节点，而另一个指向尾节点。 试想一下，当一个线程成功地获取了同步状态（或者锁），其他线程将无法获取到同步状态，转 而被构造成为节点并加入到同步队列中，而这个加入队列的过程必须要保证线程安全，因此 同步器提供了一个基于CAS的设置尾节点的方法：compareAndSetTail(Node expect,Node update)，它需要传递当前线程“认为”的尾节点和当前节点，只有设置成功后，当前节点才正式 与之前的尾节点建立关联。
  同步队列遵循FIFO，首节点是获取同步状态成功的节点，首节点的线程在释放同步状态 时，将会唤醒后继节点，而后继节点将会在获取同步状态成功时将自己设置为首节点，

- 5.2.2  独占式同步状态获取与释放

  通过调用同步器的acquire(int arg)方法可以获取同步状态，该方法对中断不敏感，也就是 由于线程获取同步状态失败后进入同步队列中，后续对线程进行中断操作时，线程不会从同步队列中移出，
  public final void acquire(int arg) {
      if (!tryAcquire(arg) &&acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
          selfInterrupt();
  }
  
  首先调用自定义同步器实现的tryAcquire(int arg)方法，该方法 保证线程安全的获取同步状态，如果同步状态获取失败，则构造同步节点（独占式 Node.EXCLUSIVE，同一时刻只能有一个线程成功获取同步状态）并通过addWaiter(Node node) 方法将该节点加入到同步队列的尾部，最后调用acquireQueued(Node node,int arg)方法，使得该 节点以“死循环”的方式获取同步状态。如果获取不到则阻塞节点中的线程，而被阻塞线程的 唤醒主要依靠前驱节点的出队或阻塞线程被中断来实现。private Node addWaiter(Node mode) {
      Node node = new Node(Thread.currentThread(), mode);
      // Try the fast path of enq; backup to full enq on failure
      Node pred = tail;
      if (pred != null) {
          node.prev = pred;
          if (compareAndSetTail(pred, node)) {
              pred.next = node;
              return node;
          }
      }
      enq(node);
      return node;
  }
  private Node enq(final Node node) {
      for (;;) {
          Node t = tail;
          if (t == null) { // Must initialize
              if (compareAndSetHead(new Node()))
                  tail = head;
          } else {
              node.prev = t;
              if (compareAndSetTail(t, node)) {
                  t.next = node;
                  return t;
              }
          }
      }
  }
  在enq(final Node node)方法中，同步器通过“死循环”来保证节点的正确添加，在“死循 环”中只有通过CAS将节点设置成为尾节点之后，当前线程才能从该方法返回，否则，当前线 程不断地尝试设置。可以看出，enq(final Node node)方法将并发添加节点的请求通过CAS变 得“串行化”了。
  节点进入同步队列之后，就进入了一个自旋的过程
  final boolean acquireQueued(final Node node, int arg) {
      boolean failed = true;
      try {
          boolean interrupted = false;
          for (;;) {
              final Node p = node.predecessor();
              if (p == head && tryAcquire(arg)) {
                  setHead(node);
                  p.next = null; // help GC
                  failed = false;
                  return interrupted;
              }
              if (shouldParkAfterFailedAcquire(p, node) &&
                  parkAndCheckInterrupt())
                  interrupted = true;
          }
      } finally {
          if (failed)
              cancelAcquire(node);
      }
  }
  而只有前驱节点是头节点才能够尝试获取同步状态，这是为什么？原因有两个，如下。 第一，头节点是成功获取到同步状态的节点，而头节点的线程释放了同步状态之后，将会 唤醒其后继节点，后继节点的线程被唤醒后需要检查自己的前驱节点是否是头节点。 第二，维护同步队列的FIFO原则。

	- 流程图![image-20200509122318572](.\picture\独占式同步状态获取流程.png)

- 5.2.3  共享式同步状态获取与释放

  共享式获取与独占式获取最主要的区别在于同一时刻能否有多个线程同时获取到同步状 态。以文件的读写为例，如果一个程序在对文件进行读操作，那么这一时刻对于该文件的写操 作均被阻塞，而读操作能够同时进行。写操作要求对资源的独占式访问，而读操作可以是共享 式访问，两种不同的访问模式在同一时刻对文件或资源的访问情况.

- 5.2.4  独占式超时获取同步状态

  通过调用同步器的doAcquireNanos(int arg,long nanosTimeout)方法可以超时获取同步状 态，即在指定的时间段内获取同步状态，如果获取到同步状态则返回true，否则，返回false。该 方法提供了传统Java同步操作（比如synchronized关键字）所不具备的特性。

	- 流程图![image-20200509122408994](.\picture\独占式超时获取同步状态.png)

- 5.2.5  自定义锁

	- TwinsLock

	  TwinsLock能够在同一时刻支持多个线程的访问，这显然是共享式 访问，因此，需要使用同步器提供的acquireShared(int args)方法等和Shared相关的方法，这就要 求TwinsLock必须重写tryAcquireShared(int args)方法和tryReleaseShared(int args)方法，这样才能 保证同步器的共享式同步状态的获取与释放方法得以执行。 其次，定义资源数。TwinsLock在同一时刻允许至多两个线程的同时访问，表明同步资源 数为2，这样可以设置初始状态status为2，当一个线程进行获取，status减1，该线程释放，则 status加1，状态的合法范围为0、1和2，其中0表示当前已经有两个线程获取了同步资源，此时 再有其他线程对同步状态进行获取，该线程只能被阻塞。在同步状态变更时，需要使用 compareAndSet(int expect,int update)方法做原子性保障。
	  public class TwinsLock implements Lock {
	      private final Sync sync = new Sync(2);
	      private static final class Sync extends AbstractQueuedSynchronizer {
	          Sync(int count) {
	              if (count <= 0) {
	                  throw new IllegalArgumentException("count must large than zero.");
	              }
	              setState(count);
	          }
	          public int tryAcquireShared(int reduceCount) {
	              for (;;) {
	                  int current = getState();
	                  int newCount = current - reduceCount;
	                  if (newCount < 0 || compareAndSetState(current,
	                          newCount)) {
	                      return newCount;
	                  }
	              }
	          }
	          public boolean tryReleaseShared(int returnCount) {
	              for (;;) {
	                  int current = getState();
	                  int newCount = current + returnCount;
	                  if (compareAndSetState(current, newCount)) {
	                      return true;
	                  }
	              }
	          }
	      }
	  //其他方法略
	  }

### 5.3  重入锁

- 5.3.1  实现重入

  重进入是指任意线程在获取到锁之后能够再次获取该锁而不会被锁所阻塞，该特性的实 现需要解决以下两个问题。 1）线程再次获取锁。锁需要去识别获取锁的线程是否为当前占据锁的线程，如果是，则再 次成功获取。
   2）锁的最终释放。线程重复n次获取了锁，随后在第n次释放该锁后，其他线程能够获取到 该锁。锁的最终释放要求锁对于获取进行计数自增，计数表示当前锁被重复获取的次数，而锁 被释放时，计数自减，当计数等于0时表示锁已经成功释放。

- 5.3.2  公平与非公平

  公平性与否是针对获取锁而言的，如果一个锁是公平的，那么锁的获取顺序就应该符合 请求的绝对时间顺序，也就是FIFO。
  
  非公平性锁可能使线程“饥饿”，为什么它又被设定成默认的实现呢？再次观察上表的结 果，如果把每次不同线程获取到锁定义为1次切换，公平性锁在测试中进行了10次切换，而非 公平性锁只有5次切换，这说明非公平性锁的开销更小。
  
  在测试中公平性锁与非公平性锁相比，总耗时是其94.3倍，总切换次数是其133倍。可以 看出，公平性锁保证了锁的获取按照FIFO原则，而代价是进行大量的线程切换。非公平性锁虽 然可能造成线程“饥饿”，但极少的线程切换，保证了其更大的吞吐量。

### 5.4  读写锁

读写锁在同一时刻可以允许多个读线程访问，但是在写线程访问时，所有的读 线程和其他写线程均被阻塞。读写锁维护了一对锁，一个读锁和一个写锁，通过分离读锁和写 锁，使得并发性相比一般的排他锁有了很大提升。

- 5.4.1  读写状态的设计

  读写锁同样依赖自定义同步器来实现同步功能，而读写状态就是其同步器的同步状态。 回想ReentrantLock中自定义同步器的实现，同步状态表示锁被一个线程重复获取的次数，而读 写锁的自定义同步器需要在同步状态（一个整型变量）上维护多个读线程和一个写线程的状 态，使得该状态的设计成为读写锁实现的关键。 如果在一个整型变量上维护多种状态，就一定需要“按位切割使用”这个变量，读写锁将 变量切分成了两个部分，高16位表示读，低16位表示写，

- 5.4.2  写锁的获取与释放

  写锁是一个支持重进入的排它锁。如果当前线程已经获取了写锁，则增加写状态。如果当 前线程在获取写锁时，读锁已经被获取（读状态不为0）或者该线程不是已经获取写锁的线程， 则当前线程进入等待状态.
  该方法除了重入条件（当前线程为获取了写锁的线程）之外，增加了一个读锁是否存在的 判断。如果存在读锁，则写锁不能被获取，原因在于：读写锁要确保写锁的操作对读锁可见，如 果允许读锁在已被获取的情况下对写锁的获取，那么正在运行的其他读线程就无法感知到当 前写线程的操作。因此，只有等待其他读线程都释放了读锁，写锁才能被当前线程获取，而写 锁一旦被获取，则其他读写线程的后续访问均被阻塞

- 5.4.3  读锁的获取与释放

  读锁是一个支持重进入的共享锁，它能够被多个线程同时获取，在没有其他写线程访问 （或者写状态为0）时，读锁总会被成功地获取，而所做的也只是（线程安全的）增加读状态。如 果当前线程已经获取了读锁，则增加读状态。如果当前线程在获取读锁时，写锁已被其他线程 获取，则进入等待状态。

- 5.4.4  锁降级

  降级指的是写锁降级成为读锁。如果当前线程拥有写锁，然后将其释放，最后再获取读 锁，这种分段完成的过程不能称之为锁降级。锁降级是指把持住（当前拥有的）写锁，再获取到 读锁，随后释放（先前拥有的）写锁的过程。

### 5.5  LockSupport

LockSupport定义了一组以park开头的方法用来阻塞当前线程，以及unpark(Thread thread) 方法来唤醒一个被阻塞的线程。Park有停车的意思，假设线程为车辆，那么park方法代表着停 车，而unpark方法则是指车辆启动离开

### 5.6  Condition接口

任意一个Java对象，都拥有一组监视器方法（定义在java.lang.Object上），主要包括wait()、 wait(long timeout)、notify()以及notifyAll()方法，这些方法与synchronized同步关键字配合，可以 实现等待/通知模式。Condition接口也提供了类似Object的监视器方法，与Lock配合可以实现等 待/通知模式。
ConditionObject是同步器AbstractQueuedSynchronizer的内部类，因为Condition的操作需要 获取相关联的锁，所以作为同步器的内部类也较为合理。每个Condition对象都包含着一个队 列（以下称为等待队列），该队列是Condition对象实现等待/通知功能的关键。

- 5.6.1  等待队列

  等待队列是一个FIFO的队列，在队列中的每个节点都包含了一个线程引用，该线程就是 在Condition对象上等待的线程，如果一个线程调用了Condition.await()方法，那么该线程将会 释放锁、构造成节点加入等待队列并进入等待状态。事实上，节点的定义复用了同步器中节点 的定义，也就是说，同步队列和等待队列中节点类型都是同步器的静态内部类 AbstractQueuedSynchronizer.Node。 一个Condition包含一个等待队列，Condition拥有首节点（firstWaiter）和尾节点 （lastWaiter）。当前线程调用Condition.await()方法，将会以当前线程构造节点，并将节点从尾部 加入等待队列

- 5.6.2  等待

  调用Condition的await()方法（或者以await开头的方法），会使当前线程进入等待队列并释 放锁，同时线程状态变为等待状态。当从await()方法返回时，当前线程一定获取了Condition相 关联的锁。 如果从队列（同步队列和等待队列）的角度看await()方法，当调用await()方法时，相当于同 步队列的首节点（获取了锁的节点）移动到Condition的等待队列中。

	- ![线程加入等待队列](.\picture\线程加入等待队列.png)

- 5.6.3  通知

  调用Condition的signal()方法，将会唤醒在等待队列中等待时间最长的节点（首节点），在 唤醒节点之前，会将节点移到同步队列中。

## 6  并发容器和框架

### 6.1  ConcurrentHashMap

ConcurrentHashMap是由Segment数组结构和HashEntry数组结构组成。Segment是一种可重 入锁（ReentrantLock），在ConcurrentHashMap里扮演锁的角色；HashEntry则用于存储键值对数 据。一个ConcurrentHashMap里包含一个Segment数组。Segment的结构和HashMap类似，是一种 数组和链表结构。一个Segment里包含一个HashEntry数组，每个HashEntry是一个链表结构的元 素，每个Segment守护着一个HashEntry数组里的元素，当对HashEntry数组的数据进行修改时， 必须首先获得与它对应的Segment锁

- 6.1.1  初始化

	- 初始化segments数组

	  segments数组的长度ssize是通过concurrencyLevel计算得出的。为了能 通过按位与的散列算法来定位segments数组的索引，必须保证segments数组的长度是2的N次方 （power-of-two size），所以必须计算出一个大于或等于concurrencyLevel的最小的2的N次方值 来作为segments数组的长度。假如concurrencyLevel等于14、15或16，ssize都会等于16，即容器里 锁的个数也是16。 注意 concurrencyLevel的最大值是65535，这意味着segments数组的长度最大为65536， 对应的二进制是16位

	- 初始化segmentShift和segmentMask

	  这两个全局变量需要在定位segment时的散列算法里使用，sshift等于ssize从1向左移位的 次数，在默认情况下concurrencyLevel等于16，1需要向左移位移动4次，所以sshift等于4。 segmentShift用于定位参与散列运算的位数，segmentShift等于32减sshift，所以等于28，这里之所 以用32是因为ConcurrentHashMap里的hash()方法输出的最大数是32位的，后面的测试中我们 可以看到这点。segmentMask是散列运算的掩码，等于ssize减1，即15，掩码的二进制各个位的 值都是1。因为ssize的最大长度是65536，所以segmentShift最大值是16，segmentMask最大值是 65535，对应的二进制是16位，每个位都是1。

	- 初始化每个segment

	  输入参数initialCapacity是ConcurrentHashMap的初始化容量，loadfactor是每个segment的负 载因子。
	  
	  cap就是segment里HashEntry数组的长度，它等于initialCapacity除以ssize 的倍数c，如果c大于1，就会取大于等于c的2的N次方值，所以cap不是1，就是2的N次方。
	  
	  segment的容量threshold＝（int）cap*loadFactor，默认情况下initialCapacity等于16，loadfactor等于 0.75，通过运算cap等于1，threshold等于零。

- 6.1.2  定位segment

  既然ConcurrentHashMap使用分段锁Segment来保护不同段的数据，那么在插入和获取元素 的时候，必须先通过散列算法定位到Segment。
  
  ConcurrentHashMap通过以下散列算法定位segment。
  final Segment segmentFor(int hash) { return segments[(hash >>> segmentShift) & segmentMask]; 
  } 
  
  默认情况下segmentShift为28，segmentMask为15，再散列后的数最大是32位二进制数据， 向右无符号移动28位，意思是让高4位参与到散列运算中，（hash>>>segmentShift） &segmentMask的运算结果分别是4、15、7和8，可以看到散列值没有发生冲突。

- 6.1.3  ConcurrentHashMap

	- get

	  get操作的高效之处在于整个get过程不需要加锁，除非读到的值是空才会加锁重读。我们 知道HashTable容器的get方法是需要加锁的，那么ConcurrentHashMap的get操作是如何做到不 加锁的呢？原因是它的get方法里将要使用的共享变量都定义成volatile类型，如用于统计当前 Segement大小的count字段和用于存储值的HashEntry的value。定义成volatile的变量，能够在线 程之间保持可见性，能够被多线程同时读，并且保证不会读到过期的值，但是只能被单线程写 （有一种情况可以被多线程写，就是写入的值不依赖于原值），在get操作里只需要读不需要写 共享变量count和value，所以可以不用加锁。之所以不会读到过期的值，是因为根据Java内存模 型的happen before原则，对volatile字段的写入操作先于读操作，即使两个线程同时修改和获取 volatile变量，get操作也能拿到最新的值，这是用volatile替换锁的经典应用场景。
	  
	  在定位元素的代码里我们可以发现，定位HashEntry和定位Segment的散列算法虽然一样， 都与数组的长度减去1再相“与”，但是相“与”的值不一样，定位Segment使用的是元素的 hashcode通过再散列后得到的值的高位，而定位HashEntry直接使用的是再散列后的值。其目的 是避免两次散列后的值一样，虽然元素在Segment里散列开了，但是却没有在HashEntry里散列 开。 hash >>> segmentShift) & segmentMask // 定位Segment所使用的hash算法 int index = hash & (tab.length - 1); // 定位HashEntry所使用的hash算法

	- put

	  由于put方法里需要对共享变量进行写入操作，所以为了线程安全，在操作共享变量时必 须加锁。put方法首先定位到Segment，然后在Segment里进行插入操作。插入操作需要经历两个 步骤，第一步判断是否需要对Segment里的HashEntry数组进行扩容，第二步定位添加元素的位 置，然后将其放在HashEntry数组里。

		- 判断扩容

		  在插入元素前会先判断Segment里的HashEntry数组是否超过容量（threshold），如果超过阈 值，则对数组进行扩容。值得一提的是，Segment的扩容判断比HashMap更恰当，因为HashMap 是在插入元素后判断元素是否已经到达容量的，如果到达了就进行扩容，但是很有可能扩容 之后没有新元素插入，这时HashMap就进行了一次无效的扩容

		- 如何扩容

		  在扩容的时候，首先会创建一个容量是原来容量两倍的数组，然后将原数组里的元素进 行再散列后插入到新的数组里。为了高效，ConcurrentHashMap不会对整个容器进行扩容，而只 对某个segment进行扩容。

	- size

	  如果要统计整个ConcurrentHashMap里元素的大小，就必须统计所有Segment里元素的大小 后求和。Segment里的全局变量count是一个volatile变量，那么在多线程场景下，是不是直接把 所有Segment的count相加就可以得到整个ConcurrentHashMap大小了呢？不是的，虽然相加时 可以获取每个Segment的count的最新值，但是可能累加前使用的count发生了变化，那么统计结 果就不准了。所以，最安全的做法是在统计size的时候把所有Segment的put、remove和clean方法 全部锁住，但是这种做法显然非常低效。 
	  
	  那么ConcurrentHashMap是如何判断在统计的时候容器是否发生了变化呢？使用modCount 变量，在put、remove和clean方法里操作元素前都会将变量modCount进行加1，那么在统计size 前后比较modCount是否发生变化，从而得知容器的大小是否发生变化。

### 6.2  ConcurrentLinkedQueue

ConcurrentLinkedQueue是一个基于链接节点的无界线程安全队列，它采用先进先出的规 则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部；当我们获取一个元 素时，它会返回队列头部的元素。它采用了“wait-free”算法（即CAS算法）来实现，该算法在 Michael&Scott算法上进行了一些修改。

- 6.2.1  结构

  ConcurrentLinkedQueue由head节点和tail节点组成，每个节点（Node）由节点元素（item）和 指向下一个节点（next）的引用组成，节点与节点之间就是通过这个next关联起来，从而组成一 张链表结构的队列。默认情况下head节点存储的元素为空，tail节点等于head节点。

- 6.2.2  入队列

  单线程入队主要做两件事情：第一是 将入队节点设置成当前队列尾节点的下一个节点；第二是更新tail节点，如果tail节点的next节 点不为空，则将入队节点设置成tail节点，如果tail节点的next节点为空，则将入队节点设置成 tail的next节点，所以tail节点不总是尾节点
  
  多线程整个入队过程主要做两件事情：第一是定位出尾节点；第二是使用 CAS算法将入队节点设置成尾节点的next节点，如不成功则重试。

- 6.2.3  出队列

  首先获取头节点的元素，然后判断头节点元素是否为空，如果为空，表示另外一个线程已 经进行了一次出队操作将该节点的元素取走，如果不为空，则使用CAS的方式将头节点的引 用设置成null，如果CAS成功，则直接返回头节点的元素，如果不成功，表示另外一个线程已经 进行了一次出队操作更新了head节点，导致元素发生了变化，需要重新获取头节点。

### 6.3  阻塞队列

阻塞队列（BlockingQueue）是一个支持两个附加操作的队列。这两个附加的操作支持阻塞 的插入和移除方法。 1）支持阻塞的插入方法：意思是当队列满时，队列会阻塞插入元素的线程，直到队列不 满。 2）支持阻塞的移除方法：意思是在队列为空时，获取元素的线程会等待队列变为非空。 阻塞队列常用于生产者和消费者的场景，生产者是向队列里添加元素的线程，消费者是 从队列里取元素的线程。阻塞队列就是生产者用来存放元素、消费者用来获取元素的容器。

- Java中的阻塞队列

	- ArrayBlockingQueue

	  ArrayBlockingQueue是一个用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原 则对元素进行排序。
	  默认情况下不保证线程公平的访问队列

	- LinkedBlockingQueue

	  LinkedBlockingQueue是一个用链表实现的有界阻塞队列。此队列的默认和最大长度为 Integer.MAX_VALUE。此队列按照先进先出的原则对元素进行排序。

	- ·PriorityBlockingQueue

	  PriorityBlockingQueue是一个支持优先级的无界阻塞队列。默认情况下元素采取自然顺序 升序排列。也可以自定义类实现compareTo()方法来指定元素排序规则，或者初始化 PriorityBlockingQueue时，指定构造参数Comparator来对元素进行排序。需要注意的是不能保证 同优先级元素的顺序。

	- DelayQueue

	  DelayQueue是一个支持延时获取元素的无界阻塞队列。队列使用PriorityQueue来实现。队 列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素。 只有在延迟期满时才能从队列中提取元素。

		- ·缓存系统的设计

		  可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询 DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。

		- 定时任务调度

		  使用DelayQueue保存当天将会执行的任务和执行时间，一旦从 DelayQueue中获取到任务就开始执行，比如TimerQueue就是使用DelayQueue实现的。

	- SynchronousQueue

	  SynchronousQueue是一个不存储元素的阻塞队列。每一个put操作必须等待一个take操作， 否则不能继续添加元素。 它支持公平访问队列。默认情况下线程采用非公平性策略访问队列。
	    SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费 者线程。队列本身并不存储任何元素，非常适合传递性场景。SynchronousQueue的吞吐量高于 LinkedBlockingQueue和ArrayBlockingQueue。

	- LinkedTransferQueue

	  LinkedTransferQueue是一个由链表结构组成的无界阻塞TransferQueue队列。相对于其他阻 塞队列，LinkedTransferQueue多了tryTransfer和transfer方法。

		- transfer方法

		  如果当前有消费者正在等待接收元素（消费者使用take()方法或带时间限制的poll()方法 时），transfer方法可以把生产者传入的元素立刻transfer（传输）给消费者。如果没有消费者在等 待接收元素，transfer方法会将元素存放在队列的tail节点，并等到该元素被消费者消费了才返 回。transfer方法的关键代码如下。 
		  Node pred = tryAppend(s, haveData); return awaitMatch(s, pred, e, (how == TIMED), nanos);
		   第一行代码是试图把存放当前元素的s节点作为tail节点。第二行代码是让CPU自旋等待 消费者消费元素。因为自旋会消耗CPU，所以自旋一定的次数后使用Thread.yield()方法来暂停 当前正在执行的线程，并执行其他线程。

		- tryTransfer方法

		  tryTransfer方法是用来试探生产者传入的元素是否能直接传给消费者。如果没有消费者等 待接收元素，则返回false。和transfer方法的区别是tryTransfer方法无论消费者是否接收，方法 立即返回，而transfer方法是必须等到消费者消费了才返回。 对于带有时间限制的tryTransfer（E e，long timeout，TimeUnit unit）方法，试图把生产者传入 的元素直接传给消费者，但是如果没有消费者消费该元素则等待指定的时间再返回，如果超 时还没消费元素，则返回false，如果在超时时间内消费了元素，则返回true。

	- LinkedBlockingDeque

	  LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列指的是可以 从队列的两端插入和移出元素。双向队列因为多了一个操作队列的入口，在多线程同时入队 时，也就减少了一半的竞争。相比其他的阻塞队列，LinkedBlockingDeque多了addFirst、 addLast、offerFirst、offerLast、peekFirst和peekLast等方法，以First单词结尾的方法，表示插入、 获取（peek）或移除双端队列的第一个元素。以Last单词结尾的方法，表示插入、获取或移除双 端队列的最后一个元素。另外，插入方法add等同于addLast，移除方法remove等效于 removeFirst

- 实现原理

  使用通知模式实现。所谓通知模式，就是当生产者往满的队列里添加元素时会阻塞住生 产者，当消费者消费了一个队列中的元素后，会通知生产者当前队列可用。
  
  当往队列里插入一个元素时，如果队列不可用，那么阻塞生产者主要通过 LockSupport.park（this）来实现。

### 6.4  Fork/Join框架

Fork/Join框架是Java 7提供的一个用于并行执行任务的框架，是一个把大任务分割成若干 个小任务，最终汇总每个小任务结果后得到大任务结果的框架。 我们再通过Fork和Join这两个单词来理解一下Fork/Join框架。Fork就是把一个大任务切分 为若干子任务并行的执行，Join就是合并这些子任务的执行结果，最后得到这个大任务的结 果。比如计算1+2+…+10000，可以分割成10个子任务，每个子任务分别对1000个数进行求和， 最终汇总这10个子任务的结果。

- 流程图![image-20200509122517509](.\picture\Fork_Join流程图.png)
- 6.4.2  工作窃取算法

  工作窃取（work-stealing）算法是指某个线程从其他队列里窃取任务来执行。
  
  假如我们需要做一个比较大的任务，可以把这个任务分割为若干 互不依赖的子任务，为了减少线程间的竞争，把这些子任务分别放到不同的队列里，并为每个 队列创建一个单独的线程来执行队列里的任务，线程和队列一一对应。比如A线程负责处理A 队列里的任务。但是，有的线程会先把自己队列里的任务干完，而其他线程对应的队列里还有 任务等待处理。干完活的线程与其等着，不如去帮其他线程干活，于是它就去其他线程的队列 里窃取一个任务来执行。而在这时它们会访问同一个队列，所以为了减少窃取任务线程和被 窃取任务线程之间的竞争，通常会使用双端队列，被窃取任务线程永远从双端队列的头部拿 任务执行，而窃取任务的线程永远从双端队列的尾部拿任务执行。

- 6.4.3  框架设计

	- 分割任务

	  首先我们需要有一个fork类来把大任务分割成子任务，有可能子任务还 是很大，所以还需要不停地分割，直到分割出的子任务足够小。

	- 执行任务合并结果

	  分割的子任务分别放在双端队列里，然后几个启动线程分 别从双端队列里获取任务执行。子任务执行完的结果都统一放在一个队列里，启动一个线程 从队列里拿数据，然后合并这些数据。

## 7  Java的原子操作类

### 7.1  原子更新基本类型类

·AtomicBoolean：原子更新布尔类型。 ·AtomicInteger：原子更新整型。 ·AtomicLong：原子更新长整型。

### 7.2  原子更新数组

·AtomicIntegerArray：原子更新整型数组里的元素。 ·AtomicLongArray：原子更新长整型数组里的元素。 ·AtomicReferenceArray：原子更新引用类型数组里的元素。 ·AtomicIntegerArray类主要是提供原子的方式更新数组里的整型

### 7.3  原子更新引用类型

·AtomicReference：原子更新引用类型。 ·AtomicReferenceFieldUpdater：原子更新引用类型里的字段。 ·AtomicMarkableReference：原子更新带有标记位的引用类型。可以原子更新一个布尔类 型的标记位和引用类型。

### 7.4  原子更新字段类

·AtomicIntegerFieldUpdater：原子更新整型的字段的更新器。 ·AtomicLongFieldUpdater：原子更新长整型字段的更新器。 ·AtomicStampedReference：原子更新带有版本号的引用类型。

## 8  Java中的并发工具类

### 8.1  CountDownLatch

CountDownLatch允许一个或多个线程等待其他线程完成操作。 

CountDownLatch不可能重新初始化或者修改CountDownLatch对象的内部计数 器的值。

一个线程调用countDown方法happen-before，另外一个线程调用await方法。

### 8.2  CyclicBarrier

CyclicBarrier默认的构造方法是CyclicBarrier（int parties），其参数表示屏障拦截的线程数 量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
当达到数量的线程到达，线程就会开始执行。

CyclicBarrier的计数器可以使用reset()方法重 置。所以CyclicBarrier能处理更为复杂的业务场景。

- 应用场景

  CyclicBarrier可以用于多线程计算数据，最后合并计算结果的场景。例如，用一个Excel保 存了用户所有银行流水，每个Sheet保存一个账户近一年的每笔银行流水，现在需要统计用户 的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日 均银行流水，最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流 水，如代码清单8-5所示。

### 8.3  Semaphore

Semaphore（信号量）是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以 保证合理的使用公共资源。

- 应用场景

  Semaphore可以用于做流量控制，特别是公用资源有限的应用场景，比如数据库连接。假 如有一个需求，要读取几万个文件的数据，因为都是IO密集型任务，我们可以启动几十个线程 并发地读取，但是如果读到内存后，还需要存储到数据库中，而数据库的连接数只有10个，这 时我们必须控制只有10个线程同时获取数据库连接保存数据，否则会报错无法获取数据库连 接。这个时候，就可以使用Semaphore来做流量控制

### 8.4  Exchanger

Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交 换。它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。这两个线程通过 exchange方法交换数据，如果第一个线程先执行exchange()方法，它会一直等待第二个线程也 执行exchange方法，当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产 出来的数据传递给对方。

- 应用场景

  Exchanger可以用于遗传算法，遗传算法里需要选出两个人作为交配对象，这时候会交换 两人的数据，并使用交叉规则得出2个交配结果。Exchanger也可以用于校对工作，比如我们需 要将纸制银行流水通过人工的方式录入成电子银行流水，为了避免错误，采用AB岗两人进行 录入，录入到Excel之后，系统需要加载这两个Excel，并对两个Excel数据进行校对，看看是否 录入一致。

## 9  Java 线程池

第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。 第二：提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。 第三：提高线程的可管理性。线程是稀缺资源，如果无限制地创建，不仅会消耗系统资源， 还会降低系统的稳定性，使用线程池可以进行统一分配、调优和监控。

### 9.1  实现原理

当向线程池提交一个任务之后，线程池是如何处理这个任务的呢？本节来看一下线程池 的主要处理流程，处理流程图如图9-1所示。 从图中可以看出，当提交一个新任务到线程池时，线程池的处理流程如下。 1）线程池判断核心线程池里的线程是否都在执行任务。如果不是，则创建一个新的工作 线程来执行任务。如果核心线程池里的线程都在执行任务，则进入下个流程。 2）线程池判断工作队列是否已经满。如果工作队列没有满，则将新提交的任务存储在这 个工作队列里。如果工作队列满了，则进入下个流程。 3）线程池判断线程池的线程是否都处于工作状态。如果没有，则创建一个新的工作线程 来执行任务。如果已经满了，则交给饱和策略来处理这个任务。

- 流程图![image-20200509122633898](.\picture\线程池实现原理.png)

### 9.2  线程池的创建参数

- corePoolSize

  线程池的基本大小：当提交一个任务到线程池时，线程池会创建一个线 程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任 务数大于线程池基本大小时就不再创建。如果调用了线程池的prestartAllCoreThreads()方法， 线程池会提前创建并启动所有基本线程。

- runnableTaskQueue

  任务队列：用于保存等待执行的任务的阻塞队列。可以选择以下几 个阻塞队列。

- maximumPoolSize

  线程池最大数量：线程池允许创建的最大线程数。如果队列满了，并 且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是，如 果使用了无界的任务队列这个参数就没什么效果。

- ThreadFactory

  用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设 置更有意义的名字。使用开源框架guava提供的ThreadFactoryBuilder可以快速给线程池里的线 程设置有意义的名字

- RejectedExecutionHandler

  饱和策略：当队列和线程池都满了，说明线程池处于饱和状 态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法 处理新任务时抛出异常。在JDK 1.5中Java线程池框架提供了以下4种策略。 ·AbortPolicy：直接抛出异常。 ·CallerRunsPolicy：只用调用者所在线程来运行任务。 ·DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。 ·DiscardPolicy：不处理，丢弃掉。

### 9.3 向线程池提交任务

可以使用两个方法向线程池提交任务，分别为execute()和submit()方法。

- execute()

  execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。 通过以下代码可知execute()方法输入的任务是一个Runnable类的实例。

- submit()

  submit()方法用于提交需要返回值的任务。线程池会返回一个future类型的对象，通过这个 future对象可以判断任务是否执行成功，并且可以通过future的get()方法来获取返回值，get()方 法会阻塞当前线程直到任务完成，而使用get（long timeout，TimeUnit unit）方法则会阻塞当前线 程一段时间后立即返回，这时候有可能任务没有执行完。

### 9.4  关闭线程池

可以通过调用线程池的shutdown或shutdownNow方法来关闭线程池。它们的原理是遍历线 程池中的工作线程，然后逐个调用线程的interrupt方法来中断线程，所以无法响应中断的任务 可能永远无法终止。但是它们存在一定的区别，shutdownNow首先将线程池的状态设置成 STOP，然后尝试停止所有的正在执行或暂停任务的线程，并返回等待执行任务的列表，而 shutdown只是将线程池的状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线 程。

### 9.5  合理地配置线程池

要想合理地配置线程池，就必须首先分析任务特性，可以从以下几个角度来分析。 ·任务的性质：CPU密集型任务、IO密集型任务和混合型任务。 ·任务的优先级：高、中和低。 ·任务的执行时间：长、中和短。 ·任务的依赖性：是否依赖其他系统资源，如数据库连接。

### 9.6  线程池的监控

如果在系统中大量使用线程池，则有必要对线程池进行监控，方便在出现问题时，可以根 据线程池的使用状况快速定位问题。可以通过线程池提供的参数进行监控，在监控线程池的 时候可以使用以下属性。 ·taskCount：线程池需要执行的任务数量。 ·completedTaskCount：线程池在运行过程中已完成的任务数量，小于或等于taskCount。 ·largestPoolSize：线程池里曾经创建过的最大线程数量。通过这个数据可以知道线程池是 否曾经满过。如该数值等于线程池的最大大小，则表示线程池曾经满过。 ·getPoolSize：线程池的线程数量。如果线程池不销毁的话，线程池里的线程不会自动销 毁，所以这个大小只增不减。 ·getActiveCount：获取活动的线程数。 通过扩展线程池进行监控。可以通过继承线程池来自定义线程池，重写线程池的 beforeExecute、afterExecute和terminated方法，也可以在任务执行前、执行后和线程池关闭前执 行一些代码来进行监控。例如，监控任务的平均执行时间、最大执行时间和最小执行时间等。 这几个方法在线程池里是空方法。

## 10  Executor

