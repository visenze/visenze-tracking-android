package com.visenze.datatracking;

import com.google.gson.Gson;
import com.visenze.datatracking.data.DataTrackingResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostEventUnitTest {

    @Mock
    private Call<DataTrackingResponse> pushCall;

    private Response<DataTrackingResponse> getMockResponse() {
        String respStr ="{\"status\":\"OK\",\"reqid\":\"74a1a615-05ce-4b4e-b24f-fb664c6a0382\",\"result\":[{\"code\":0,\"message\":\"success\"},{\"code\":104,\"message\":\"Parameter 'action' is required.\"}]}";
        Gson gson = new Gson();
        DataTrackingResponse response = gson.fromJson(respStr, DataTrackingResponse.class);

        Response<DataTrackingResponse> resp = Response.success(response);
        return resp;
    }


    @Before
    public void setUp() throws Exception {
        when(pushCall.execute()).thenReturn(getMockResponse());
    }


    @Test
    public void postEvent_isSuccess() {
        try {
            Response<DataTrackingResponse> response = pushCall.execute();
            if(response.isSuccessful()) {
                DataTrackingResponse sucResp = response.body();


                assertEquals("OK", sucResp.getStatus());
            }
        } catch (Exception ex) {
            System.out.println("error: "+ ex.getMessage());
            assertTrue("exception occur", false);
        }


    }

}
