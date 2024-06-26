package by.bsuir.news.service;

import by.bsuir.news.dto.request.NewsRequestTo;
import by.bsuir.news.dto.response.NewsResponseTo;
import by.bsuir.news.entity.Editor;
import by.bsuir.news.entity.News;
import by.bsuir.news.exception.ClientException;
import by.bsuir.news.repository.EditorRepository;
import by.bsuir.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private EditorRepository editorRepository;

    public NewsResponseTo create(NewsRequestTo request) throws ClientException {
        Optional<Editor> editor = editorRepository.findById(request.getEditorId());
        if(editor.isEmpty()) {
            throw new ClientException("Editor with specified id doesn't exist");
        }
        News news = NewsRequestTo.fromRequest(request);
        news.setEdit(editor.get());
        news.setCreated(Date.valueOf(LocalDate.now()));
        news.setModified(Date.valueOf(LocalDate.now()));
        return NewsResponseTo.toResponse(newsRepository.save(news));
    }

    public List<NewsResponseTo> getAll() {
        List<News> news = newsRepository.findAll();
        return  !news.isEmpty() ? news.stream().map(NewsResponseTo::toResponse).collect(Collectors.toList()) : new ArrayList<NewsResponseTo>();
    }

    public NewsResponseTo getById(Long id) throws ClientException {
        Optional<News> news = newsRepository.findById(id);
        if(news.isEmpty()) {
            throw new ClientException("News not found");
        }
        return  NewsResponseTo.toResponse(news.get());
    }

    public NewsResponseTo update(NewsRequestTo request) throws ClientException {
        Optional<News> currNews = newsRepository.findById(request.getId());
        if (currNews.isEmpty()) {
            throw new ClientException("News with specified id don't exist");
        }
        Optional<Editor> editor = editorRepository.findById(request.getEditorId());
        if (editor.isEmpty()) {
            throw new ClientException("Editor with the specified id doesn't exist");
        }
        News news = NewsRequestTo.fromRequest(request);
        news.setEdit(editor.get());
        news.setCreated(currNews.get().getCreated());
        news.setModified(Date.valueOf(LocalDate.now()));
        return NewsResponseTo.toResponse(newsRepository.save(news));
    }

    public Long delete(Long id) throws ClientException {
        if(newsRepository.findById(id).isEmpty()) {
            throw new ClientException("News with specified id don't exist");
        }
        newsRepository.deleteById(id);
        if(newsRepository.findById(id).isPresent()) {
            throw new ClientException("Failed to delete the news");
        }
        return id;
    }

}
