# data-reader
This Java project reads data from CSV and Excel files and maps the content into Java DTOs (Data Transfer Objects).

## Features

- Supports both CSV and Excel (.xlsx) file formats.
- Maps input data to custom Java DTOs.
- Uses Apache POI for Excel parsing.
- Easily extendable for different data structures or DTOs.
- Includes TestNG setup for testing.

## Dependencies

The project uses the following main dependencies:

- [Apache POI](https://poi.apache.org/) (`poi`, `poi-ooxml`) – For reading `.xlsx` Excel files.
- [TestNG](https://testng.org/) – For writing and running tests.

All dependencies are managed via Maven in `pom.xml`.
