# cxf-validation-frontend-example

An example of [`cxf-validation-frontend`](https://github.com/fillumina/cxf-validation-frontend) inside
a real build, and the functional test of that wiring. The `cxf-codegen-plugin` runs the frontend over
`wsdl/Hello.wsdl`, the generated sources are compiled by the same build, and a test reads what came
out of it.

```xml
<plugin>
  <groupId>org.apache.cxf</groupId>
  <artifactId>cxf-codegen-plugin</artifactId>
  <executions>
    <execution>
      <id>wsdl2java</id>
      <phase>generate-sources</phase>
      <configuration>
        <wsdlOptions>
          <wsdlOption>
            <wsdl>${project.basedir}/wsdl/Hello.wsdl</wsdl>
            <extraargs>
              <extraarg>-frontend</extraarg>
              <extraarg>bean-validation</extraarg>
              <extraarg>-xjc-XCxfValidationFrontendOptions:generateAnnotations=inOut</extraarg>
            </extraargs>
          </wsdlOption>
        </wsdlOptions>
      </configuration>
      <goals>
        <goal>wsdl2java</goal>
      </goals>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>com.fillumina</groupId>
      <artifactId>cxf-validation-frontend</artifactId>
      <version>${cxf-validation-frontend.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## Building

The build needs JDK 21 and Maven, and nothing else:

```
mvn -B verify -Dcxf-validation-frontend.version=<version>
```

The version is a property, so the example runs against a release from Maven Central and against a
snapshot without being touched. The snapshot side needs `mvn install` in the project itself first:

```
cd ../cxf-validation-frontend && mvn -B install
cd ../cxf-validation-frontend-example && mvn -B verify -Dcxf-validation-frontend.version=1.0.0-SNAPSHOT
```

This is a single project rather than a parent with one module per integration, which is the shape of
the examples of the old line: that line had several plugins to exercise, this one has a single
artifact.
