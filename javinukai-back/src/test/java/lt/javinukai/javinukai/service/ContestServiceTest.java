package lt.javinukai.javinukai.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import lt.javinukai.javinukai.dto.request.contest.CategoryDTO;
import lt.javinukai.javinukai.dto.request.contest.ContestDTO;
import lt.javinukai.javinukai.entity.Category;
import lt.javinukai.javinukai.entity.Contest;
import lt.javinukai.javinukai.mapper.ContestMapper;
import lt.javinukai.javinukai.repository.CategoryRepository;
import lt.javinukai.javinukai.repository.ContestRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ContestServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ContestRepository contestRepository;
    @InjectMocks
    private ContestService contestService;

//    @Test
//    public void createContestReturnsContest() {
//
//        final UUID categoryID = UUID.randomUUID();
//        final Category category01 = Category.builder()
//                .id(categoryID)
//                .categoryName("test category name")
//                .description("test category description")
//                .totalSubmissions(66)
//                .build();
//
//        final UUID contestID = UUID.randomUUID();
//        final Contest contest01 = Contest.builder()
//                .id(contestID)
//                .contestName("test contest name")
//                .description("test contest description")
//                .totalSubmissions(666)
//                .categories(Collections.singletonList(category01))
//                .startDate(ZonedDateTime.now())
//                .endDate(ZonedDateTime.now())
//                .build();
//
//        final ContestDTO contestDTO01 = ContestMapper.contestToContestDTO(contest01);
//        contestDTO01.setCategories(Collections.singletonList(category01));
//
//        when(contestRepository.save(Mockito.any(Contest.class))).thenReturn(contest01);
//        when(categoryRepository.findById(categoryID)).thenReturn(Optional.ofNullable(category01));
//
//        final Contest createdContest = contestService
//                .createContest(ContestMapper.contestToContestDTOTest(contest01));
//
//        Assertions.assertThat(createdContest).isNotNull();
//        Assertions.assertThat(createdContest.getCategories()).isNotNull();
//    }

//        @Test
//        public void retrieveAllContestsReturnsPageOfContests() {
//
//            final Contest contest01 = Contest.builder()
//                    .contestName("test contest name")
//                    .description("test contest description")
//                    .totalSubmissions(666)
//                    .startDate(ZonedDateTime.now())
//                    .endDate(ZonedDateTime.now())
//                    .build();
//
//            final List<Contest> expectedContestList = new ArrayList<>();
//            expectedContestList.add(contest01);
//
//            Page<Contest> page = new PageImpl<>(expectedContestList);
//
//            when(contestRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
//
//            Page<Contest> retriecedPage = contestService.retrieveAllContests(1, 10);
//
//            assertNotNull(retriecedPage);
//            assertEquals(expectedContestList.size(), retriecedPage.getContent().size());
//        }

//    @Test
//    public void retrieveAllContestsReturnsEmptyList() {
//
//        final Page<Contest> emptyPage = new PageImpl<>(new ArrayList<>());
//
//        when(contestRepository.findAll(Mockito.any(Pageable.class))).thenReturn(emptyPage);
//
//        final Page<Contest> retrievedPage = contestService.retrieveAllContests(0, 10);
//
//        assertNotNull(retrievedPage);
//        assertTrue(retrievedPage.isEmpty());
//    }

    @Test
    void retrieveContestReturnsContest() {
        UUID id = UUID.randomUUID();

        Category category01 = Category.builder()
                .name("gamta")
                .description("ir taip aišku ")
                .totalSubmissions(100)
                .build();

        Contest expectedContest = Contest.builder()
                .name("viltis")
                .description("paskutinė, nepabegusi")
                .categories(Collections.singletonList(category01))
                .totalSubmissions(666)
                .startDate(ZonedDateTime.now())
                .endDate(ZonedDateTime.now())
                .build();

        when(contestRepository.findById(eq(id))).thenReturn(Optional.of(expectedContest));
        Contest result = contestService.retrieveContest(id);

        assertNotNull(result);
        assertEquals(expectedContest, result);

        verify(contestRepository, times(1)).findById(eq(id));
    }

//    @Test
//    void updateCategoriesOfContestReturnsContest() {
//
//        final UUID categoryID = UUID.randomUUID();
//        final Category category = new Category(categoryID,
//                "test category name",
//                "testCategory description",
//                66,
//                null, null, null, null);
//        List<Category> categories = new ArrayList<>();
//        categories.add(category);
//
//        when(categoryRepository.findById(categoryID)).thenReturn(Optional.of(category));
//
//        final UUID contestID = UUID.randomUUID();
//        final Contest initialContest = new Contest(contestID,
//                "test contest name",
//                "test contest description",
//                null,
//                666,
//                null, null, null, null);
//
//        when(contestRepository.findById(contestID)).thenReturn(Optional.ofNullable(initialContest));
//        when(contestRepository.save(any(Contest.class))).thenReturn(initialContest);
//
//        final Contest updatedContest = contestService.updateCategoriesOfContest(initialContest.getId(), categories);
//
//        Assertions.assertThat(updatedContest).isNotNull();
//        Assertions.assertThat(updatedContest.getId()).isNotNull();
//    }

    @Test
    void deleteContestSuccess() {
        UUID contestId = UUID.randomUUID();

        when(contestRepository.existsById(eq(contestId))).thenReturn(true);
        assertDoesNotThrow(() -> contestService.deleteContest(contestId));

        verify(contestRepository, times(1)).existsById(eq(contestId));
        verify(contestRepository, times(1)).deleteById(eq(contestId));
    }

    @Test
    void deleteContestFail() {
        UUID contestId = UUID.randomUUID();

        when(contestRepository.existsById(eq(contestId))).thenReturn(false);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            contestService.deleteContest(contestId);
        });

        Assertions.assertThat(exception.getMessage()).
                isEqualTo("Contest was not found with ID: " + contestId);
    }

}