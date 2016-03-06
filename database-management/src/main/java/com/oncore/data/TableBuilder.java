package com.oncore.data;

import com.oncore.common.configure.CommonConfigure;
import com.oncore.common.model.TableElement;
import com.oncore.data.file.FileCreator;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by steve on 3/6/16.
 */
@Component
public class TableBuilder {
    BlockingQueue<TableElement> queue = new LinkedBlockingQueue<>();

    private ExecutorService executorService;

    @Autowired
    public TableBuilder(CommonConfigure commonConfigure) {
        executorService = Executors.newFixedThreadPool(commonConfigure.getCreate_table_thread_number());
        for (int i = 0; i < commonConfigure.getCreate_table_thread_number(); i++) {
            Creator creator = new Creator();
            executorService.submit(creator);
        }
    }

    public void createMappingFile(TableElement e) throws InterruptedException {
        queue.put(e);
    }

    class Creator implements Runnable {
        @Autowired
        @Qualifier("hibernateMappingFileCreator")
        FileCreator fileCreator;

        @Override
        public void run() {
            while (true) {
                TableElement element = null;
                try {
                    element = queue.take();
                    System.out.println("creating file for " + element.getName());
                    fileCreator.createFile(element);

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    if (element != null) {
                        try {
                            queue.put(element);
                        } catch (InterruptedException e1) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }
}
