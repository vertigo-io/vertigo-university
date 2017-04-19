/**
 * These routes are generated automatically.
 * Facade io.vertigo.pandora.webservices.masterdata.MasterdataWebservices
 */

const ROOT = "/masterdata";
import urlBuilder from 'focus-core/util/url/builder';


const urlGetGenderList = urlBuilder('/masterdata/genders', 'GET')
export function getGenderList() {
  /* response DtList<Gender> */  
  return urlGetGenderList();
}

