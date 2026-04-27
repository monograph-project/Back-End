package com.final_project.versioncontrolservice.service;
import com.final_project.versioncontrolservice.model.*;
import com.final_project.versioncontrolservice.repo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContributionService {

    private final PullRequestRepository prRepository;
    private final MongoTemplate mongoTemplate;

    public ContributionService(PullRequestRepository prRepository, MongoTemplate mongoTemplate) {
        this.prRepository = prRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Get contribution statistics for a repository
     */
    public ContributionStats getContributionStats(String owner, String repo) {
        // Get merged PRs
        List<PullRequestDocument> mergedPRs = prRepository.findByRepoOwnerAndRepoName(owner, repo)
                .stream()
                .filter(pr -> "merged".equals(pr.getStatus()))
                .collect(Collectors.toList());

        Map<String, ContributorInfo> contributors = new HashMap<>();
        int totalCommits = 0;
        int totalAdditions = 0;
        int totalDeletions = 0;

        for (PullRequestDocument pr : mergedPRs) {
            String author = pr.getAuthor();
            ContributorInfo info = contributors.getOrDefault(author, new ContributorInfo(author));

            info.addPR();
            info.addCommit(); // Each PR merge counts as a commit

            // In a real implementation, you'd calculate actual additions/deletions
            // from the diff between source and target branches
            info.addAdditions(10); // Placeholder
            info.addDeletions(5);  // Placeholder

            totalCommits++;
            totalAdditions += 10;
            totalDeletions += 5;

            contributors.put(author, info);
        }

        // Calculate percentages
        for (ContributorInfo info : contributors.values()) {
            info.calculatePercentages(totalAdditions, totalDeletions);
        }

        List<ContributorInfo> sortedContributors = contributors.values().stream()
                .sorted((a, b) -> Integer.compare(b.getTotalPRs(), a.getTotalPRs()))
                .collect(Collectors.toList());

        return new ContributionStats(
                sortedContributors,
                totalCommits,
                totalAdditions,
                totalDeletions,
                mergedPRs.size()
        );
    }

    /**
     * Get contribution graph data (like GitHub's contribution heatmap)
     */
    public ContributionGraph getContributionGraph(String owner, String repo, String username) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(1);

        List<PullRequestDocument> userPRs = prRepository
                .findByRepoOwnerAndRepoName(owner, repo)
                .stream()
                .filter(pr -> pr.getAuthor().equals(username))
                .filter(pr -> pr.getCreatedAt() != null)
                .collect(Collectors.toList());

        Map<LocalDate, Integer> dailyContributions = new HashMap<>();

        for (PullRequestDocument pr : userPRs) {
            LocalDate date = pr.getCreatedAt()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            dailyContributions.merge(date, 1, Integer::sum);
        }

        List<ContributionDay> days = new ArrayList<>();
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            int count = dailyContributions.getOrDefault(current, 0);
            days.add(new ContributionDay(current.toString(), count));
            current = current.plusDays(1);
        }

        return new ContributionGraph(username, days);
    }

    /**
     * Get user activity feed
     */
    public List<ActivityEvent> getUserActivity(String username, int limit) {
        List<ActivityEvent> events = new ArrayList<>();

        // Get user's PRs
        List<PullRequestDocument> userPRs = prRepository.findAll()
                .stream()
                .filter(pr -> pr.getAuthor().equals(username))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit)
                .collect(Collectors.toList());

        for (PullRequestDocument pr : userPRs) {
            ActivityEvent event = new ActivityEvent(
                    pr.getId().toHexString(),
                    "pull_request",
                    pr.getStatus(),
                    pr.getTitle(),
                    pr.getRepoOwner() + "/" + pr.getRepoName(),
                    pr.getCreatedAt()
            );
            events.add(event);
        }

        return events;
    }

    // ─── DTOs ─────────────────────────────────────────────────────────────

    public static class ContributionStats {
        private List<ContributorInfo> contributors;
        private int totalCommits;
        private int totalAdditions;
        private int totalDeletions;
        private int totalPRs;

        public ContributionStats(List<ContributorInfo> contributors, int totalCommits,
                                 int totalAdditions, int totalDeletions, int totalPRs) {
            this.contributors = contributors;
            this.totalCommits = totalCommits;
            this.totalAdditions = totalAdditions;
            this.totalDeletions = totalDeletions;
            this.totalPRs = totalPRs;
        }

        public List<ContributorInfo> getContributors() { return contributors; }
        public int getTotalCommits() { return totalCommits; }
        public int getTotalAdditions() { return totalAdditions; }
        public int getTotalDeletions() { return totalDeletions; }
        public int getTotalPRs() { return totalPRs; }
    }

    public static class ContributorInfo {
        private String username;
        private int totalPRs;
        private int totalCommits;
        private int additions;
        private int deletions;
        private double additionPercentage;
        private double deletionPercentage;

        public ContributorInfo(String username) {
            this.username = username;
        }

        public void addPR() { this.totalPRs++; }
        public void addCommit() { this.totalCommits++; }
        public void addAdditions(int count) { this.additions += count; }
        public void addDeletions(int count) { this.deletions += count; }

        public void calculatePercentages(int totalAdditions, int totalDeletions) {
            this.additionPercentage = totalAdditions > 0 ?
                    (double) additions / totalAdditions * 100 : 0;
            this.deletionPercentage = totalDeletions > 0 ?
                    (double) deletions / totalDeletions * 100 : 0;
        }

        public String getUsername() { return username; }
        public int getTotalPRs() { return totalPRs; }
        public int getTotalCommits() { return totalCommits; }
        public int getAdditions() { return additions; }
        public int getDeletions() { return deletions; }
        public double getAdditionPercentage() { return additionPercentage; }
        public double getDeletionPercentage() { return deletionPercentage; }
    }

    public static class ContributionGraph {
        private String username;
        private List<ContributionDay> contributions;

        public ContributionGraph(String username, List<ContributionDay> contributions) {
            this.username = username;
            this.contributions = contributions;
        }

        public String getUsername() { return username; }
        public List<ContributionDay> getContributions() { return contributions; }
    }

    public static class ContributionDay {
        private String date;
        private int count;

        public ContributionDay(String date, int count) {
            this.date = date;
            this.count = count;
        }

        public String getDate() { return date; }
        public int getCount() { return count; }
    }

    public static class ActivityEvent {
        private String id;
        private String type;
        private String action;
        private String title;
        private String repo;
        private Instant timestamp;

        public ActivityEvent(String id, String type, String action, String title, String repo, Instant timestamp) {
            this.id = id;
            this.type = type;
            this.action = action;
            this.title = title;
            this.repo = repo;
            this.timestamp = timestamp;
        }

        public String getId() { return id; }
        public String getType() { return type; }
        public String getAction() { return action; }
        public String getTitle() { return title; }
        public String getRepo() { return repo; }
        public Instant getTimestamp() { return timestamp; }
    }
}
