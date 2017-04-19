/**
 * These routes are generated automatically.
 * Facade io.vertigo.pandora.webservices.movies.MovieWebServices
 */

const ROOT = "/movies";
import urlBuilder from 'focus-core/util/url/builder';


const urlSearch = urlBuilder('/movies/search', 'POST')
export function search({/*String*/ criteria, /*UiSelectedFacets*/ facets, /*String,optional*/ group, /*integer*/ listState.top, /*integer,optional*/ listState.skip, /*String,optional*/ listState.sortFieldName, /*boolean,optional*/ listState.sortDesc}) {
  /* response FacetedQueryResult */  
  return urlSearch({bodyData:{criteria, facets, group}, queryData: {listState.top, listState.skip, listState.sortFieldName, listState.sortDesc}});
}

const urlUpdateMovieTrailer = urlBuilder('/movies/${id}/trailer', 'PUT')
export function updateMovieTrailer({/*long*/ id, /*MovieTrailer*/ movieTrailer}) {
  /* response MovieTrailer */  
  return urlUpdateMovieTrailer({bodyData:{...movieTrailer}, urlData:{id}});
}

const urlUpdateMovieSynopsis = urlBuilder('/movies/${id}/synopsis', 'PUT')
export function updateMovieSynopsis({/*long*/ id, /*MovieSynopsis*/ movieSynopsis}) {
  /* response MovieSynopsis */  
  return urlUpdateMovieSynopsis({bodyData:{...movieSynopsis}, urlData:{id}});
}

const urlUpdateMovieCaract = urlBuilder('/movies/${id}/caract', 'PUT')
export function updateMovieCaract({/*long*/ id, /*MovieCaract*/ movieCaract}) {
  /* response MovieCaract */  
  return urlUpdateMovieCaract({bodyData:{...movieCaract}, urlData:{id}});
}

const urlGetMarkRanking = urlBuilder('/movies/rankings/mark', 'GET')
export function getMarkRanking() {
  /* response List<Movie> */  
  return urlGetMarkRanking();
}

const urlGetDateRanking = urlBuilder('/movies/rankings/date', 'GET')
export function getDateRanking() {
  /* response List<Movie> */  
  return urlGetDateRanking();
}

const urlUpdateMovie = urlBuilder('/movies/${id}', 'PUT')
export function updateMovie({/*long*/ id, /*Movie*/ movie}) {
  /* response Movie */  
  return urlUpdateMovie({bodyData:{...movie}, urlData:{id}});
}

const urlCreateMovie = urlBuilder('/movies/', 'POST')
export function createMovie({/*Movie*/ movie}) {
  /* response void */  
  return urlCreateMovie({bodyData:{...movie}});
}

const urlGetMovieHeader = urlBuilder('/movies/${id}/header', 'GET')
export function getMovieHeader({/*long*/ id}) {
  /* response Movie */  
  return urlGetMovieHeader({urlData:{id}});
}

const urlGetMovieCaract = urlBuilder('/movies/${id}/caract', 'GET')
export function getMovieCaract({/*long*/ id}) {
  /* response MovieCaract */  
  return urlGetMovieCaract({urlData:{id}});
}

const urlGetMovieSynopsis = urlBuilder('/movies/${id}/synopsis', 'GET')
export function getMovieSynopsis({/*long*/ id}) {
  /* response MovieSynopsis */  
  return urlGetMovieSynopsis({urlData:{id}});
}

const urlGetMovieTrailer = urlBuilder('/movies/${id}/trailer', 'GET')
export function getMovieTrailer({/*long*/ id}) {
  /* response MovieTrailer */  
  return urlGetMovieTrailer({urlData:{id}});
}

const urlGetCasting = urlBuilder('/movies/${id}/casting', 'GET')
export function getCasting({/*long*/ id}) {
  /* response UiContext */  
  return urlGetCasting({urlData:{id}});
}

const urlGetMovie = urlBuilder('/movies/${id}', 'GET')
export function getMovie({/*long*/ id}) {
  /* response Movie */  
  return urlGetMovie({urlData:{id}});
}

const urlSearchMovies = urlBuilder('/movies/search', 'GET')
export function searchMovies({/*String*/ q, /*String,optional*/ group, /*integer*/ listState.top, /*integer,optional*/ listState.skip, /*String,optional*/ listState.sortFieldName, /*boolean,optional*/ listState.sortDesc}) {
  /* response FacetedQueryResult<MovieIndex, io.vertigo.dynamo.search.model.SearchQuery> */  
  return urlSearchMovies({queryData: {q, group, listState.top, listState.skip, listState.sortFieldName, listState.sortDesc}});
}

