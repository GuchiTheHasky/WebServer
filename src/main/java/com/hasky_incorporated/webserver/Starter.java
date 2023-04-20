package com.hasky_incorporated.webserver;

public class Starter {
}

// TODO: content type & content length, можливо не потрібні.
// TODO: apachi http client для тестування Сервера
// TODO: глянути специфікацію http
// TODO: має бути CRLF а не \n чи \r\n
// TODO: System.line.Separator -> повертає той роздільник, який в мене є в системі(тому він не підходить для цього таску
//  бо потрібно завжди повертати, щось одне(конкретне))
// TODO: якось заюзати клас Request;
// TODO: створити метод badRequestException() створити клас з ексепшинами і заекстендитись від класу RunTimeException;
// TODO: треба буде кидати ексепшин і створити цей клас;
// TODO: в рісорсРідері не треба явно вказувати читати відповідь з індкса, він якимось чином повинен вирішити,
