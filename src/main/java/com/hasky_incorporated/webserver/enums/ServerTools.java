package com.hasky_incorporated.webserver.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ServerTools {
    HTML("\\index.html"),
    CSS("\\css\\style.css"),
    IMG("\\img\\image.jpg");

    private String path;
}
