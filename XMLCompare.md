```java
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;

public class AppTest {
    @Test
    public void test() {
        DefaultNodeMatcher nodeMatcher = new DefaultNodeMatcher(ElementSelectors.byNameAndText);
        Diff d = DiffBuilder.compare(Input.fromStream(AppTest.class.getResourceAsStream("/data2.xml"))).withTest(Input.fromStream(AppTest.class.getResourceAsStream("/data1.xml")))
                .checkForSimilar()
                .withNodeMatcher(nodeMatcher)
                .ignoreWhitespace()
                .ignoreComments()
                .build();

        assert !d.hasDifferences();
    }
}
```

```xml
<!-- https://mvnrepository.com/artifact/org.xmlunit/xmlunit-core -->
<dependency>
  <groupId>org.xmlunit</groupId>
  <artifactId>xmlunit-core</artifactId>
  <version>2.8.2</version>
  <scope>test</scope>
</dependency>
```

https://stackoverflow.com/questions/51428038/comparing-xml-equal-files-except-for-whitespace-with-xmlunit-produces-differen
