# function-backend-qa
Automation test project for function backend service

---

## How to Run
1. Go to `pom.xml` and check for value of `spring.config.location`
2. Specify which properties file will be used (default is `application-local.properties`)
3. If testing only several features, change the default value in `DefinitionTestSuite` with the specified features' tag
4. Separate each feature's API in a separate package and steps folder (e.g., testing authentication may create 
package `api.auth` and steps `steps.Auth`; **NOTE** the use of capital case characters)
5. Each API should extend BaseAPI to be able to use value of properties configuration
6. After finish developing test, **REVERT** the value of `spring.config.location` in `pom.xml` **AND** `tags` in
 `DefinitionTestSuite`, then commit  

---

## Testing Notes:
- Use `@Regression` for features which will be tested without specifying specific tags
- Use `@Positive` for features which are tested in positive condition (happy flow)
- Use `@Negative` for features which are tested in negative condition
- Add custom annotation for features to ease testing separate feature (e.g.: Sanity test uses `@Sanity`)
- If needed, add more properties value in the properties file and also the Java file
