package com.jgzy.entity.common;

/**
 * 模版消息，带url
 */
public class TemplateWithUrl extends Template {
    // 模板消息详情链接
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toJSON() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.getToUser())).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.getTemplateId())).append(",");
        buffer.append(String.format("\"url\":\"%s\"", this.url)).append(",");
        buffer.append(String.format("\"topcolor\":\"%s\"", this.getTopColor())).append(",");
        buffer.append("\"data\":{");
        StringBuilder sb = new StringBuilder();
        for (TemplateParam param : this.getTemplateParamList()) {
            sb.append(String.format("\"%s\":{\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));
            sb.append(",");
        }
        buffer.append(sb.substring(0, sb.length() - 1));
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }
}