package com.fogok.io;

import com.fogok.io.fsystem.Files;
import com.fogok.io.logging.Logging;

public final class Fgkio {
    public static final Files files = Files.getInstance();
    public static final Logging logging = Logging.getInstance();
}
