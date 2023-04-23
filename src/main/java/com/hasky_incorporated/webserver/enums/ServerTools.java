package com.hasky_incorporated.webserver.enums;


import lombok.Getter;

@Getter
public enum ServerTools {
    HTML("\\index.html"),
    CSS("\\css\\style.css"),
    IMG("\\img\\image.jpg");

    private final String path;

    ServerTools(String path) {
        this.path = path;
    }
}
