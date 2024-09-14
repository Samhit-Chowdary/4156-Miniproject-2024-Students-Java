# COMS 4156: Advanced Software Engineering Individual Project

### Author: Samhit Chowdary Bhogavalli
### UNI: sb4845

* I used the following plugin for pmd static bug analyzer.
```declarative
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.25.0</version>
    <configuration>
        <rulesets>
            <ruleset>/rulesets/java/maven-pmd-plugin-default.xml</ruleset>
        </rulesets>
    </configuration>
</plugin>
```
* I ran the pmd check with the command: "mvn pmd::check".
* I ran the style check with the command: "mvn checkstyle:check".
* Current Unit Test Coverage: 55%.