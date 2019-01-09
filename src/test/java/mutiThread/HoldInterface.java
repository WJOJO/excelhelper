package mutiThread;

/**
 * @author Javon Wang
 * @description 持有接口
 * @create 2019-01-09 14:25
 */
public class HoldInterface {

    public Interface anInterface;

    public HoldInterface(Interface anInterface){
        this.anInterface = anInterface;
    }

    public void start(){
        anInterface.print();
    }

}
