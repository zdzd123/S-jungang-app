package com.jgzy.entity.common;

import java.util.List;

/**
 * 模版消息，不带url
 */
public class Template {

    // 消息接收方  
    private String toUser;
    // 模板id  
    private String templateId;
    // 消息顶部的颜色  
    private String topColor;
    // 参数列表  
    private List<TemplateParam> templateParamList;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public String toJSON() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.templateId)).append(",");
        buffer.append(String.format("\"topcolor\":\"%s\"", this.topColor)).append(",");
        buffer.append("\"data\":{");
        StringBuilder sb = new StringBuilder();
        for (TemplateParam param : templateParamList) {
            sb.append(String.format("\"%s\":{\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));
            sb.append(",");
        }
        buffer.append(sb.substring(0, sb.length() - 1));
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }

    public List<TemplateParam> getTemplateParamList() {
        return templateParamList;
    }

    public void setTemplateParamList(List<TemplateParam> templateParamList) {
        this.templateParamList = templateParamList;
    }

}  