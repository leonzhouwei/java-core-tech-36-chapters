package cn.edu.xidian.sc.leonzhou.chap11;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class NioFileCopyByByteBuffer {

    private static final String FROM = "var/chap11/from/a.mkv";
    private static final String TO = "var/chap11/to/a.mkv";

    public static void main(String[] args) throws IOException {
        // --- delete ---
        File toFile = new File(TO);
        if (toFile.exists()) {
            boolean success = toFile.delete();
            Preconditions.checkArgument(success, "deleting " + TO + " failed");
        }

        Stopwatch stopwatch = Stopwatch.createStarted();

        // --- copy ---
        Path fromPath = Paths.get(FROM);
        Path toPath = Paths.get(TO);
        // also tested for 4, 16, 32, 64, 128, 256, 512 and 1024
        int bufferSizeKB = 128;
        int bufferSize = bufferSizeKB * 1024;
        try (FileChannel fromChannel = FileChannel.open(fromPath, StandardOpenOption.READ);
             FileChannel toChannel = FileChannel.open(toPath, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE))) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            int bytesCount;
            while ((bytesCount = fromChannel.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                toChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        }

        // --- count time ---
        stopwatch.stop();
        System.out.println("copy by nio done, timeCost=" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

}
