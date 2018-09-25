package ru.job4j.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

/**
 * Контроллер загрузки файлов.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class FileUploadController extends HttpServlet {
    private Random random = new Random(System.currentTimeMillis());
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Загружает файл на сервер, отправляет ответ содержащий новое имя файла.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(req);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        writer.append(processUploadedFile(item));
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        writer.flush();
    }

    /**
     * Обрабатывает объект, сохраняет его в новый файл и генерирует ему новое имя
     * @param item объект для сохранения.
     * @return имя сохраненного файла.
     */
    private String processUploadedFile(FileItem item) {
        File uploadedFile;
        String fileName = null;
        do {
            String path = getServletContext().getRealPath("/upload/" + random.nextInt() + item.getName());
            System.out.println(path);
            uploadedFile = new File(path);
        } while (uploadedFile.exists());
        try {
            uploadedFile.createNewFile();
            item.write(uploadedFile);
            fileName = uploadedFile.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
