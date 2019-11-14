package ua.com.nc.nctrainingproject.persistance.dao.postgre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.com.nc.nctrainingproject.models.Author;
import ua.com.nc.nctrainingproject.models.Book;
import ua.com.nc.nctrainingproject.persistance.dao.AbstractDAO;
import ua.com.nc.nctrainingproject.persistance.dao.postgre.queries.BookQuery;

import ua.com.nc.nctrainingproject.persistance.dao.postgre.queries.UserQuery;

import ua.com.nc.nctrainingproject.persistance.mappers.BookRowMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookPostgreDAO extends AbstractDAO<Book> {

  private final AuthorBookPostgreDAO authorBookPostgreDAO;

  @Autowired
  public BookPostgreDAO(JdbcTemplate jdbcTemplate, AuthorBookPostgreDAO authorBookPostgreDAO) {
    super(jdbcTemplate);
    this.authorBookPostgreDAO = authorBookPostgreDAO;
  }

  public Book getBookById(int bookId) {
    Book book = super.getEntityById(BookQuery.GET_BOOK_BY_ID, new BookRowMapper(), bookId);
    book.setAuthors(authorBookPostgreDAO.getAuthorsByBookId(bookId));
    return book;
  }

  public List<Book> getAllBooks() {
    List<Book> books = super.getAllEntities(BookQuery.GET_ALL_BOOKS, new BookRowMapper());
    for (Book book : books) {
      book.setAuthors(authorBookPostgreDAO.getAuthorsByBookId(book.getId()));
    }
    return books;
  }

  public void deleteBookById(int bookId) {
    super.deleteEntityById(BookQuery.DELETE_BOOK_BY_ID, bookId);
  }

  public void createBook(Book book) {
    jdbcTemplate.update(BookQuery.CREATE_BOOK, book.getHeader(), book.getOverview(), book.getFileId(), book.getStatus(),
      book.getGenreId(), book.getPhotoId());

    for (Author author : book.getAuthors()) {
      authorBookPostgreDAO.createAuthorBookConnection(book.getId(), author.getId());
    }
  }

  public void updateBookById(int id, Book book) {
    Object[] params = new Object[]{book.getHeader(), book.getOverview(), book.getFileId(), book.getStatus(), book.getGenreId(), book.getPhotoId(), id};
    super.updateEntityById(id, params, BookQuery.UPDATE_BOOK);
  }
}

