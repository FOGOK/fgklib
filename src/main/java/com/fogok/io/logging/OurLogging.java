package com.fogok.io.logging;

import com.esotericsoftware.minlog.Log;
import com.fogok.io.Fgkio;

public class OurLogging {
    public static void debug(String s) {
        Log.debug(Fgkio.class.getName(), s);
    }
    public static void error(String s) {
        Log.error(Fgkio.class.getName(), s);
    }
}
