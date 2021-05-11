package test;

import com.mor.BaseFunc;
import com.mor.api.VeevaAPI;
import com.mor.utility.Utility;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;


public class Main {
    public static final String INIT_CODE = "initCode";
    BaseFunc baseFunc = new BaseFunc();
    VeevaAPI veevaAPI = new VeevaAPI();
    Utility utility = new Utility();

    //Test #1:
    @Test
    public void xorTest() {
        int x = 17;
        while (true){
            x = x ^ 1;
            System.out.print(x + " ,");
        }
    }

    //Test #2:
    @Test
    public void pyramidNumber(){
        int num = 134521;
        assertTrue(baseFunc.isPyramid(num));

    }

    @Test
    public void testHappyFlow() throws IOException {
        String newCode = veevaAPI.getData(INIT_CODE);
        assertTrue(utility.isCodeReceived(newCode));
        String codeForCSV = veevaAPI.sendCode(newCode);
        assertNotEquals(newCode, codeForCSV);
        System.out.println("data was accepted");
        String csvReady = veevaAPI.sendCode(codeForCSV);
        assertNotEquals(csvReady, "processing");
        assertTrue(veevaAPI.downloadCSVFileFromServer(csvReady));
    }
}
