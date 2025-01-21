# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Changed
- Reduce footprint from inlining `get()` and `getLazy()` of `FlexibleHilt`

### Removed
- `MakeFlexible` annotation
- `getFromFlexibleHilt()` and `lazyFromFlexibleHilt()` methods

## [v0.6.0]
### Changed
- Generated class name so it doesn't match original class

## [v0.5.0]
### Added
- `FlexibleHilt.get()` and `FlexibleHilt.getLazy()`

### Changed
- Generated classes and their file structure

### Deprecated
- `MakeFlexible` annotation, as it is no longer needed due to recent compiler changes
- `getFromFlexibleHilt()` and `lazyFromFlexibleHilt()` in favor of `FlexibleHilt.get()` and `FlexibleHilt.getLazy()`

## [v0.4.0]
### Changed
- Add guard on what `Context` can be used to initialize `FlexibleHilt`

## [v0.3.0]
### Changed
- `FlexibleHilt` is now automatically initialized

## [v0.2.1]
### Changed
- Now requires `minSdk` to be 21+

## [v0.1.1]
### Changed
- Use `me.tawsif` group id for library

## [v1.0.0]
Initial release

[Unreleased]: https://github.com/dewantawsif/flexible-hilt/compare/v0.6.0...HEAD
[v0.6.0]: https://github.com/dewantawsif/flexible-hilt/compare/v0.5.0...v0.6.0
[v0.5.0]: https://github.com/dewantawsif/flexible-hilt/compare/v0.4.0...v0.5.0
[v0.4.0]: https://github.com/dewantawsif/flexible-hilt/compare/v0.3.0...v0.4.0
[v0.3.0]: https://github.com/dewantawsif/flexible-hilt/compare/v0.2.1...v0.3.0
[v0.2.1]: https://github.com/dewantawsif/flexible-hilt/compare/v0.1.1...v0.2.1
[v0.1.1]: https://github.com/dewantawsif/flexible-hilt/compare/e64922653292368f7d74ec0d92e4e80ad44b95eb...v0.1.1
[v1.0.0]: https://github.com/dewantawsif/flexible-hilt/commits/e64922653292368f7d74ec0d92e4e80ad44b95eb
