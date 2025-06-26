package com.tej.smart_lms.services;

import com.tej.smart_lms.dto.BlogDto;
import com.tej.smart_lms.dto.BlogSectionDto;
import com.tej.smart_lms.model.Blog;
import com.tej.smart_lms.model.BlogSection;
import com.tej.smart_lms.repository.BlogRepository;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired private BlogRepository blogRepository;
    @Autowired private UserRepository userRepository;

    @PostConstruct
    public void initVersionFix() {
        blogRepository.patchNullVersions();
    }
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Blog not found"));
    }

    public List<Blog> getBlogsByCategory(String category) {
        return blogRepository.findByCategoryIgnoreCase(category);
    }
    public Map<String, Long> getCompletedBlogCountByCategory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<String> completedTitles = user.getCompletedBlogs();
        if (completedTitles == null || completedTitles.isEmpty()) return Collections.emptyMap();

        return blogRepository.findAll().stream()
                .filter(blog -> completedTitles.contains(blog.getTitle()))
                .collect(Collectors.groupingBy(
                        blog -> blog.getCategory().toLowerCase().replaceAll("\\s+", ""),
                        Collectors.counting()
                ));
    }



    public List<Blog> getBlogsByDifficulty(String difficulty) {
        return blogRepository.findByDifficultyIgnoreCase(difficulty);
    }

    public Blog createOrUpdateBlog(BlogDto dto) {
        Blog blog;

        if (dto.getId() != null && blogRepository.existsById(dto.getId())) {
            blog = blogRepository.findById(dto.getId()).orElseThrow();

            blog.setTitle(dto.getTitle());
            blog.setCategory(dto.getCategory());
            blog.setDifficulty(dto.getDifficulty());
            blog.setDescription(dto.getDescription());
            blog.setTags(dto.getTags());
            blog.setTechnologies(dto.getTechnologies());

            blog.getSections().clear();
            if (dto.getSections() != null) {
                blog.getSections().addAll(
                        dto.getSections().stream()
                                .map(this::mapToSection)
                                .toList()
                );
            }

        } else {
            blog = new Blog();
            blog.setTitle(dto.getTitle());
            blog.setCategory(dto.getCategory());
            blog.setDifficulty(dto.getDifficulty());
            blog.setDescription(dto.getDescription());
            blog.setTags(dto.getTags());
            blog.setTechnologies(dto.getTechnologies());

            blog.setSections(dto.getSections().stream()
                    .map(this::mapToSection)
                    .toList());
        }

        return blogRepository.save(blog);
    }

    private BlogSection mapToSection(BlogSectionDto dto) {
        BlogSection s = new BlogSection();
        s.setTitle(dto.getTitle());
        s.setType(dto.getType());
        s.setDescription(dto.getDescription());
        s.setLanguage(dto.getLanguage());
        s.setContent(dto.getContent());
        return s;
    }






    public void markBlogCompleted(String username, String blogTitle) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<String> completed = Optional.ofNullable(user.getCompletedBlogs())
                .orElse(new HashSet<>());

        completed.add(blogTitle);
        user.setCompletedBlogs(completed);

        userRepository.save(user); // ✅ saving the managed entity
    }


    public int getCompletedBlogCount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return Optional.ofNullable(user.getCompletedBlogs()).map(Set::size).orElse(0);
    }

    public List<String> getCompletedBlogs(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return new ArrayList<>(Optional.ofNullable(user.getCompletedBlogs()).orElse(Collections.emptySet()));
    }
    public long getTotalBlogCount() {
        return blogRepository.count();
    }

    public Map<String, Long> getBlogCountByCategory() {
        return blogRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        blog -> blog.getCategory().toLowerCase(),
                        Collectors.counting()
                ));
    }

}
