package com.oncore.data.file;

import com.oncore.data.file.uploader.UpLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by steve on 3/8/16.
 */
@Service
public class ReportTemplateBuilder {
    //    BlockingQueue
    Log log = LogFactory.getLog(ReportTemplateBuilder.class);
    BlockingQueue<ReportElement> reportElements = new LinkedBlockingQueue<>();

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    public ReportTemplateBuilder() {
        executorService.submit(new UploaderRunner());
        executorService.submit(new UploaderRunner());
        executorService.submit(new UploaderRunner());
    }


    public void uploadReport(ReportElement reportElement) {
        try {
            this.reportElements.put(reportElement);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    @Autowired
    @Qualifier(value = "aWSUploader")
    UpLoader upLoader;

    class UploaderRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    ReportElement reportElement = reportElements.take();
                    upLoader.upload(reportElement.getContent(), reportElement.getReportTableName());
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }


    public class ReportElement {

        public ReportElement(String reportTableName, String content) {
            this.reportTableName = reportTableName;
            this.content = content;
        }

        private String content;
        private String reportTableName;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReportTableName() {
            return reportTableName;
        }

        public void setReportTableName(String report_id) {
            this.reportTableName = report_id;
        }
    }
}
