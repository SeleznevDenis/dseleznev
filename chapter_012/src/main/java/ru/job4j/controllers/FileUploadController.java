package ru.job4j.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Контроллер загрузки файлов.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@RestController
@RequestMapping("/file")
public class FileUploadController extends HttpServlet {
    private Random random = new Random(System.currentTimeMillis());
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Загружает файл на сервер, отправляет ответ содержащий новое имя файла.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public String handleUpload(HttpServletRequest req) {
        LOG.error("in fileUpload");
        StringBuilder result = new StringBuilder();
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
            factory.setFileCleaningTracker(null);
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(req);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        result.append(processUploadedFile(item, req));
                    }
                }
            } catch (FileUploadException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        LOG.error("file name");
        LOG.error(result.toString());
        return result.toString();
    }


    /**
     * Обрабатывает объект, сохраняет его в новый файл и генерирует ему новое имя
     * @param item объект для сохранения.
     * @return имя сохраненного файла.
     */
    private String processUploadedFile(FileItem item, HttpServletRequest req) {
        File uploadedFile;
        String fileName = null;
        do {
            String path = req.getSession().getServletContext().getRealPath("/upload/" + random.nextInt() + item.getName());
            LOG.error(path);
            uploadedFile = new File(path);
        } while (uploadedFile.exists());
        try {
            uploadedFile.createNewFile();
            item.write(uploadedFile);
            fileName = uploadedFile.getName();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return fileName;
    }
}

