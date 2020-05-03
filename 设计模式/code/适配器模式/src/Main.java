public class Main {

    public static void main(String[] args) {
        //现在我有一只火鸡
        Turkey turkey=new WildTurkey();

        //我想让这只火鸡 拥有duck的属性
        Duck duck=new TurkeyAdapter(turkey);
        //实际执行的是在adapter中选择的方法，是把Turkey和duck连接起来。
        //turkey--》type-c USB,adapter--》充电器大头，duck--》相当于插座
        //我现在需要把type-c usb插入到插头当中，就需要一个中间的一个适配器来完成。
        duck.quack();
        duck.fly();
    }
}
