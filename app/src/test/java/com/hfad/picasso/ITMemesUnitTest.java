package com.hfad.picasso;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hfad.picasso.util.InternetConnection;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ITMemesUnitTest {
    
    @Test
    public void checkConnectionNoConnectionReturnFalse() {
        //Arrange
        ConnectivityManager testConnectionManager = mock(ConnectivityManager.class);
        Context testContext = mock(Context.class);
        when (testContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(testConnectionManager);
        when(testConnectionManager.getActiveNetworkInfo()).thenReturn(null);

        //Act
        boolean result = InternetConnection.checkConnection(testContext);

        //Assert
        assertFalse(result);
    }

    @Test
    public void checkConnectionConnectedReturnTrue(){
        //Arrange
        ConnectivityManager testConnectionManager = mock(ConnectivityManager.class);
        Context testContext = mock(Context.class);
        NetworkInfo testNetworkInfo = mock(NetworkInfo.class);
        when (testContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(testConnectionManager);
        when(testConnectionManager.getActiveNetworkInfo()).thenReturn(testNetworkInfo);

        //Act
        boolean result = InternetConnection.checkConnection(testContext);

        //Assert
        assertTrue(result);
    }
}