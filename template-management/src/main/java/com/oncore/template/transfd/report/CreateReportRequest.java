package com.oncore.template.transfd.report;

import com.oncore.template.transfd.ElementRequest;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by steve on 1/22/16.
 */
@XmlRootElement
public class CreateReportRequest extends ElementRequest {

//    @NotNull
//    @Valid
//    @Size(min = 1, max = 16)
//    List<ReportFieldRequest> fields;

    @NotNull
    @Valid
    @Length(min = 32)
    private String content;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
