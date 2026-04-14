package org.dromara;

import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;


/**
 * 启动程序
 *
 * @author Lion Li
 */

@SolonMain
public class DromaraApplication {

    public static void main(String[] args) {
        
        
        Solon.start(DromaraApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  RuoYi-Vue-Plus启动成功   ლ(´ڡ`ლ)ﾞ");
    }

}
