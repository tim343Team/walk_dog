package com.wallet.walkthedog;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.reflect.TypeToken;
import com.wallet.walkthedog.net.RemoteData;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {


        ListTest<RemoteData<String>> test = new ListTest<RemoteData<String>>() {

            @Override
            void doing(RemoteData<String> stringRemoteData) {
                //String covert = ListTest.covert(stringRemoteData);
            }
        };

        System.out.println("\n");
        create("");

    }

    public static <T> void create(T t){

        Type type = new TypeToken<T>() {
        }.getType();
        System.out.println(type.toString());
    }


}