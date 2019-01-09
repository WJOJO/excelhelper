package mutiThread;

/**
 * @author Javon Wang
 * @description 接口
 * @create 2019-01-09 14:23
 */
public interface Interface {

    void print();

    default void println(){
        System.out.println("调用default方法");
    }
}
