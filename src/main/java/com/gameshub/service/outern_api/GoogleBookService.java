package com.gameshub.service.outern_api;

import com.gameshub.client.BookClient;
import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.book.googleBook.GoogleBookListDto;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.GoogleBookNotFoundException;
import com.gameshub.exception.GoogleBooksNotFoundException;
import com.gameshub.mapper.game.RawgGameNameMapper;
import com.gameshub.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoogleBookService {

    private final BookClient bookClient;
    private final GameRepository gameRepository;

    public Set<GoogleBookDto> fetchListOfGoogleBooksRelatedToGameByGameName(final String gameName) throws GoogleBooksNotFoundException {
        Set<GoogleBookDto> books = bookClient.getBooksList(gameName)
                                              .orElseThrow(GoogleBooksNotFoundException::new)
                                              .getItems();
        return filterBooksCategory(books);
    }

    public Set<GoogleBookDto> fetchListOfGoogleBooksRelatedToGameByGameId(final Long gameId) throws GameNotFoundException, GoogleBooksNotFoundException {
        String gameName = RawgGameNameMapper.mapSlugNameToGameName(gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new).getName());
        return fetchListOfGoogleBooksRelatedToGameByGameName(gameName);
    }

    public GoogleBookListDto retrieveGoogleBookById(final String googleBookId) throws GoogleBookNotFoundException {
        return bookClient.getBookById(googleBookId).orElseThrow(GoogleBookNotFoundException::new);
    }

    private Set<GoogleBookDto> filterBooksCategory(final Set<GoogleBookDto> books) {
        List<String> bookIds = new ArrayList<>();
        return books.stream()
                .filter(book -> {
                        List<String> categories = book.getVolumeInfo().getCategories();
                        bookIds.add(book.getId());
                            if (categories != null) {
                                return categories.contains("Games") || categories.contains("Computers") ||
                                       categories.contains("Fiction") || categories.contains("Comics & Graphic Novels") ||
                                       categories.contains("Game") && !bookIds.contains(book.getId());
                            }
                                return false;
                            })
                .filter(book -> {
                        String language = book.getVolumeInfo().getLanguage();
                        return language.equals("pl") || language.equals("en");
                } )
                .collect(Collectors.toSet());
    }
}
