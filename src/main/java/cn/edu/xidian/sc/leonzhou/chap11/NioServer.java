package cn.edu.xidian.sc.leonzhou.chap11;

import cn.edu.xidian.sc.leonzhou.consts.SocketConsts;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wei Zhou
 */
public class NioServer implements Runnable {

    private int port;

    private ExecutorService listenerThreadPool;

    public static void main(String[] args) throws IOException, InterruptedException {
        NioServer server = new NioServer(8080);
        server.startup();
        Thread.sleep(3000L);
        try (Socket client = new Socket(SocketConsts.LOCALHOST, server.getPort())) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferedReader.lines().forEach(s -> System.out.println(s));
        }
    }

    public NioServer(int port) {
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
    }

    @Override
    public void run() {
        try (Selector selector = Selector.open(); ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress(this.port));
            serverSocket.configureBlocking(false);
            // 注册到 Selector，并说明关注点
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                // 阻塞等待就绪的 Channel，这是关键点之一
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    sayHelloWord((ServerSocketChannel) key.channel());
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sayHelloWord(ServerSocketChannel server) throws IOException {
        try (SocketChannel client = server.accept()) {
            client.write(Charset.defaultCharset().encode("Hello world!" + Thread.currentThread().getName()));
        }
    }

    public int getPort() {
        return port;
    }
}