package cn.edu.xidian.sc.leonzhou.chap11;

import cn.edu.xidian.sc.leonzhou.consts.SocketConsts;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wei Zhou
 */
public class BioServer implements Runnable {

    private int port;

    private ExecutorService listenerThreadPool;
    private ExecutorService handlerThreadPool;

    public static void main(String[] args) throws IOException {
        BioServer server = new BioServer(8080);
        server.startup();
        try (Socket client = new Socket(SocketConsts.LOCALHOST, server.getPort())) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferedReader.lines().forEach(s -> System.out.println(s));
        }
    }

    public BioServer(int port) {
        this.port = port;
    }

    public void startup() {
        init();
        listenerThreadPool.execute(this);
    }

    private void init() {
        listenerThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(), new ThreadFactoryBuilder().setNameFormat(this.getClass().getSimpleName() + "_ListenerThreadPool_%s").build());
        handlerThreadPool = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(), new ThreadFactoryBuilder().setNameFormat(this.getClass().getSimpleName() + "_HandlerThreadPool_%s").build());
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                RequestHandler hanlder = new RequestHandler(serverSocket.accept());
                handlerThreadPool.execute(hanlder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandler implements Runnable {

        private Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
                out.println("Hello world!" + Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return port;
    }
}
