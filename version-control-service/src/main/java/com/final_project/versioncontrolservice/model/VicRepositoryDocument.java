package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "vic_repositories")
public class VicRepositoryDocument {

    @Id
    private String id;

    private String owner;
    private String name;
    private String description;
    private String visibility = "private";
    private List<Collaborator> collaborators = new ArrayList<>();
    private Map<String, String> branchHeads = new HashMap<>();
    private String symbolicHead = "refs/heads/main";

    public static String compositeId(String owner, String name) {
        return owner.toLowerCase() + "/" + name.toLowerCase();
    }
}
