package try_catch;

import java.io.IOException;
/**
 * 总结
 * 函数结束动作:抛出异常或return
 * 在try或catch代码块中触发函数结束的动作将在finally代码块执行完之后再执行。
 * */
public class TryCatchTest {
    public static void main(String[] args) {
        testException();
        testReturn();
    }

    public enum EName{
        AException,
        BException,
        CException,
        NotException
    }

    public static void testException(){
        // 在try catch出现无法处理的异常将在finally语句结束后抛出，同时finally之后的语句将不再执行
        testExceptionTry(EName.NotException); // 12567
        System.out.println();
        testExceptionTry(EName.AException);// 134567
        System.out.println();
        testExceptionTry(EName.BException);// 156+BException
        System.out.println();
        testExceptionCatch(EName.AException); // 13456+CException
        System.out.println();
        testExceptionFinally(EName.BException);// 134+AException
    }

    public static void testExceptionTry(EName eName){
        try {
            System.out.print("1");
            throwE(eName);
            System.out.print("2");
        }catch(AException e){
            System.out.print("3");
            System.out.print("4");
        }finally {
            System.out.print("5");
            System.out.print("6");
        }
        System.out.print("7");
    }

    public static void testExceptionCatch(EName eName){
        try {
            System.out.print("1");
            throwE(eName);
            System.out.print("2");
        }catch(AException e){
            System.out.print("3");
            System.out.print("4");
            throwE(EName.BException);
        }finally {
            System.out.print("5");
            System.out.print("6");
            throwE(EName.CException);
        }
        System.out.print("7");
    }

    public static void testExceptionFinally(EName eName){
        try {
            System.out.print("1");
            throwE(eName);
            System.out.print("2");
        }finally {
            System.out.print("3");
            System.out.print("4");
            throwE(EName.AException);
        }
        System.out.print("5");
    }

    public static void throwE(EName eName) {
        if (eName == EName.AException)
            throw new AException();
        else if (eName == EName.BException)
            throw new BException();
        else if (eName == EName.CException)
            throw new CException();
    }

    public static class AException extends RuntimeException{
    }

    public static class BException extends RuntimeException{
    }

    public static class CException extends RuntimeException{
    }

    public static void testReturn(){
        // 执行完finally之后再执行try或catch中的return,如果finally中存在return则不再执行try或catch中的return
        // 即try catch中的return将延迟执行
        System.out.println(testReturnTry()); // 121
        System.out.println(testReturnCatch1()); // 1232
        System.out.println(testReturnCatch2()); // 1231
        System.out.println(testReturnFinally()); // 122
    }

    public static int testReturnTry(){
        try {
            System.out.print("1");
            return 1;
        }finally {
            System.out.print("2");
        }
    }

    public static int testReturnCatch1(){
        try {
            System.out.print("1");
            throw new AException();
        }catch (AException e){
            System.out.print("2");
            return 1;
        }
        finally {
            System.out.print("3");
            return 2;
        }

    }

    public static int testReturnCatch2(){
        try {
            System.out.print("1");
            throw new AException();
        }catch (AException e){
            System.out.print("2");
            return 1;
        }
        finally {
            System.out.print("3");
        }
    }

    public static int testReturnFinally(){
        try {
            System.out.print("1");
            return 1;
        }finally {
            System.out.print("2");
            return 2;
        }
    }


}
