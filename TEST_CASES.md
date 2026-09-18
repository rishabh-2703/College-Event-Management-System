# Test Cases

| ID | Test | Expected Result |
|---|---|---|
| TC01 | Valid student login | Student menu opens |
| TC02 | Invalid login | Error message |
| TC03 | New user registration | User saved |
| TC04 | Duplicate email | Registration rejected |
| TC05 | Organizer creates event | Event saved as PENDING |
| TC06 | Admin approves event | Status becomes APPROVED |
| TC07 | Student registers | Registration saved |
| TC08 | Student registers twice | DuplicateRegistrationException |
| TC09 | Event reaches capacity | EventFullException |
| TC10 | Cancel registration | Status becomes CANCELLED |
| TC11 | Rating outside 1-5 | Validation error |
| TC12 | Program restart | Data remains in data/*.txt |
