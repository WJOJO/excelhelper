package mutiThread;

import org.junit.Test;

/**
 * @author Javon Wang
 * @description 实现
 * @create 2019-01-09 14:24
 */
public class Impl {

    @Test
    public void testInterface(){
        HoldInterface holdInterface = new HoldInterface(() -> System.out.println("hello创建接口对象"));
        holdInterface.start();

        holdInterface.anInterface.println();
    }




}
