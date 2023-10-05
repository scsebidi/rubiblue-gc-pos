package coza.rubiblue.nativepos;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import android.os.Build;
import android.view.Gravity;

import cn.weipass.pos.sdk.LatticePrinter;
import cn.weipass.pos.sdk.LatticePrinter.*;
import cn.weipass.pos.sdk.Weipos.*;
import cn.weipass.pos.sdk.impl.WeiposImpl;
import cn.weipass.pos.sdk.IPrint.*;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
@NativePlugin(
        requestCodes={GlocellPosModule.REQUEST_CODE,GlocellPosModule.PRINT_REQUEST_CODE}
)
public class GlocellPosModule extends Plugin {
    protected static final int REQUEST_CODE = 1; // Unique request code
    protected static final int PRINT_REQUEST_CODE = 2;
    public static int tsn=1;
    public static String lastSentTsn="";
    private PluginCall mReturnResults;

    @PluginMethod
    public void getSerial(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            String SerialNumber = Build.SERIAL;
            if (SerialNumber == "unknown") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    SerialNumber = Build.getSerial();
                }
            }
            ret.put("value", SerialNumber);
            call.success(ret);
        } catch (Exception ex) {
            JSObject ret = new JSObject();
            ret.put("value", "unknown");
            call.success(ret);
        }
    }

    @PluginMethod()
    public void print(PluginCall call) {
        try {
            final FontSize size = FontSize.MEDIUM;
            final FontStyle style = FontStyle.NORMAL;

            final String receiptContent = call.getString("ReceiptText");
            final String receiptLogo = call.getString("ReceiptLogo");

            mReturnResults = call;

            WeiposImpl.as().init(getContext(), new OnInitListener() {
                @Override
                public void onInitOk() {
                    String deviceInfo = WeiposImpl.as().getDeviceInfo();
                    LatticePrinter latticePrinter = WeiposImpl.as().openLatticePrinter();
                    try{

                        byte[] encodeByte = Base64.getDecoder().decode(receiptLogo);

                        latticePrinter.printImage(encodeByte,cn.weipass.pos.sdk.IPrint.Gravity.CENTER);
                    }
                    catch(Exception d){
                        latticePrinter.printText("Exception", LatticePrinter.FontFamily.SONG, size, style);
                    }


                    String[] receiptContents = receiptContent.split("\\|");
                    if (receiptContents.length > 0) {
                        for (String line : receiptContents) {
                            latticePrinter.printText(line, LatticePrinter.FontFamily.SONG, size, style);
                            latticePrinter.printText("\n", LatticePrinter.FontFamily.SONG, size, style);
                        }
                    } else {
                        latticePrinter.printText(receiptContent, LatticePrinter.FontFamily.SONG, size, style);
                    }
                    latticePrinter.submitPrint();
                    JSObject ret = new JSObject();
                    ret.put("value", "latticePrinter.submitPrint()");
                    mReturnResults.success(ret);
                    WeiposImpl.as().destroy();
                }

                @Override
                public void onError(String s) {
                    JSObject ret = new JSObject();
                    ret.put("value", s);
                    mReturnResults.success(ret);
                    WeiposImpl.as().destroy();
                }

                @Override
                public void onDestroy() {
                    JSObject ret = new JSObject();
                    ret.put("value", "onDestroy");
                    mReturnResults.success(ret);
                    // listener.onPrinterClosed("");
                }
            });
        } catch (Exception ex) {
            JSObject ret = new JSObject();
            ret.put("value", "printing failed " + ex.getMessage());
            mReturnResults.success(ret);
        }
    }
}
