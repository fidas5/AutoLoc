import fs from 'node:fs/promises';
import path from 'node:path';
import { pathToFileURL } from 'node:url';
import { Presentation, PresentationFile } from 'file:///C:/Users/MSI/.cache/codex-runtimes/codex-primary-runtime/dependencies/node/node_modules/@oai/artifact-tool/dist/artifact_tool.mjs';

const root = 'C:/Users/MSI/Desktop/Esprit/Architecture_SI/AutoLoc';
const build = path.join(root,'.presentation-build');
const skill = 'C:/Users/MSI/.codex/plugins/cache/openai-primary-runtime/presentations/26.1105.12137/skills/presentations';
const src = 'autoloc-api/src/main/java/tn/esprit/autoloc/autolocapi/';
const read = async p => (await fs.readFile(path.join(root,src,p),'utf8')).replaceAll('\r','');
const iface = await read('service/IReservationService.java');
const base = await read('service/decorator/ReservationServiceDecorator.java');
const deco = await read('service/decorator/ReservationValidationDecorator.java');
const core = await read('service/ReservationServiceImpl.java');
const ctrl = await read('web/controller/ReservationController.java');
const p = Presentation.create({slideSize:{width:1280,height:720}});
const C={bg:'#F5F3EE',ink:'#172D38',muted:'#526570',accent:'#007C78',white:'#FFFFFF',dark:'#142B36',light:'#C4E9E2'};
const fontPolicy={basis:'design',families:['Arial','Consolas']};
function text(s,t,x,y,w,h,size=28,color=C.ink,bold=false,font='Arial'){
 const a=s.shapes.add({geometry:'textbox',name:t.slice(0,45),position:{left:x,top:y,width:w,height:h},fill:'none',line:{fill:'none',width:0}});
 a.text=t;a.text.style={fontSize:size,typeface:font,color,bold,insets:0,autoFit:'none',wrap:'none'};return a;
}
function slide(title,section,notes,dark=false){
 const s=p.slides.add();s.background.fill=dark?C.dark:C.bg;
 const i=p.slides.items.length;
 text(s,section.toUpperCase(),64,39,1100,25,16,dark?C.light:C.accent,true);
 text(s,title,64,88,1150,110,44,dark?C.white:C.ink,true);
 text(s,`AUTOLOC   /   DÉCORATEUR`,64,671,900,22,14,dark?C.light:C.muted);
 text(s,String(i).padStart(2,'0')+' / 15',1100,671,115,22,14,dark?C.light:C.muted);
 s.speakerNotes.text=notes;return s;
}
function code(s,t,y=220,size=24){text(s,t,64,y,1150,420,size,C.ink,false,'Consolas');}
function rows(s,items,y=220,width=1110){items.forEach(([head,body],i)=>{text(s,head,64,y+i*110,width,36,29,C.accent,true);text(s,body,64,y+43+i*110,width,57,25);});}
async function image(s,file,alt,left,top,width,height){const bytes=await fs.readFile(path.join(build,file));s.images.add({blob:bytes.buffer.slice(bytes.byteOffset,bytes.byteOffset+bytes.byteLength),contentType:'image/png',alt,fit:'contain',position:{left,top,width,height}});}
function table(s,values,widths,y=215,h=360){
 const t=s.tables.add({rows:values.length,columns:values[0].length,left:64,top:y,width:1152,height:h,columnWidths:widths,values});
 t.borders.assign({fill:'#D6DEDA',width:1,style:'solid'});
 for(let r=0;r<values.length;r++)for(let c=0;c<values[0].length;c++){
  const cell=t.getCell(r,c);cell.fill=r===0?C.dark:(r%2?C.white:C.bg);
  cell.text.style={typeface:'Arial',fontSize:23,bold:r===0,color:r===0?C.white:C.ink,insets:12};
 }
 return t;
}
const source=(...files)=>'\n\nSources AutoLoc : '+files.map(x=>src+x).join('\n');
const spring='\nRéférences Spring : https://docs.spring.io/spring-framework/reference/core/beans/annotation-config/autowired-qualifiers.html et https://docs.spring.io/spring-framework/docs/6.0.8/javadoc-api/org/springframework/context/annotation/Primary.html';

{
 const s=slide('Le patron de conception\nDécorateur','Génie logiciel · Patrons de conception',
 'Présenter le Decorator comme un patron structurel qui enrichit un objet en conservant son interface. Notre exemple vient du service de réservation d’AutoLoc. Le dépôt initial ne contenait pas ce patron. Nous l’avons ajouté en déplaçant une règle existante, sans introduire une nouvelle fonctionnalité métier. Le parcours couvre le problème, les rôles, le code réel, la composition Spring et les limites. Les extraits de code des diapositives suivantes proviennent de la version modifiée du projet.'+source('service/decorator/ReservationValidationDecorator.java'),true);
 text(s,'Application au projet AutoLoc',64,295,1120,60,39,C.light);
 text(s,'Une règle existante, isolée dans un objet qui enveloppe le service.',64,420,650,95,28,C.white);
 text(s,'Java 17   ·   Spring Boot   ·   Spring Data JPA',64,570,1120,36,23,C.light);
 await image(s,'auto-decorator.png','Voiture AutoLoc au centre des couches du patron Décorateur',760,240,460,259);
}
{
 const s=slide('Le périmètre réel d’AutoLoc','01 · Contexte',
 'AutoLoc gère la location de véhicules. Le pom.xml déclare Java 17, Spring Boot 4.1.1, Spring Data JPA, Maven et Lombok. L’architecture comprend les contrôleurs HTTP, les services et les repositories. La réservation dispose notamment d’un identifiant, de dates et de relations vers le client, le véhicule et le contrat. Attention : la présence de ces attributs ne prouve pas qu’un contrôle de disponibilité ou un calcul de prix existe. Ici, nous utilisons uniquement la règle d’identifiant réellement présente dans create. Les commentaires relatifs aux dates ne sont pas une validation exécutée.'+source('domain/Reservation.java','service/ReservationServiceImpl.java')+'\nSource : autoloc-api/pom.xml',false);
 rows(s,[['Architecture en couches','Controller : HTTP. Service : opérations métier. Repository : accès JPA.'],['Exemple retenu','La création d’une réservation via IReservationService.create().'],['Règle conservée','Une nouvelle réservation ne doit pas avoir d’identifiant.']]);
 text(s,'Aucun calcul tarifaire ni contrôle de disponibilité ajouté.',64,586,1150,43,24,C.muted);
}
{
 const s=slide('Le problème de conception','02 · Motivation',
 'Dans la version initiale, ReservationServiceImpl.create vérifiait que getId était null puis appelait reservationRepository.save. Le comportement était simple et correct pour cette règle. Le problème étudié est organisationnel : le même service porte la validation d’entrée et la persistance. Pour enseigner le patron, nous isolons la première responsabilité. Une sous-classe de chaque variante serait peu pratique si les combinaisons se multipliaient. Pour une seule règle, le code initial restait tout à fait défendable : le Décorateur ajoute une complexité qu’il faut justifier.'+source('service/ReservationServiceImpl.java','service/decorator/ReservationValidationDecorator.java'));
 rows(s,[['Avant la modification','ReservationServiceImpl.create() vérifiait l’identifiant puis sauvegardait.'],['Responsabilités réunies','Le contrôle de création et l’accès au repository vivaient dans la même méthode.'],['Objectif de la refactorisation','Séparer ces responsabilités en conservant le contrat public et le résultat.']]);
}
{
 const s=slide('Le principe du Décorateur','03 · Principe',
 'Un décorateur implémente le même contrat que l’objet qu’il contient. Le client continue donc à utiliser cette interface. Le décorateur reçoit son délégué par composition et ajoute un comportement avant ou après la délégation. Dans AutoLoc, le contrôle s’exécute avant create et peut interrompre l’appel. La base abstraite utilise l’héritage pour partager la délégation, mais l’enrichissement repose bien sur la composition via le champ delegate. Plusieurs décorateurs pourraient s’envelopper, bien que le projet n’en utilise ici qu’un seul. Référence illustrative du patron Wrapper/Decorator : https://www.oracle.com/java/technologies/filters.html');
 text(s,'Même interface, objet enveloppé, comportement ajouté',64,215,1152,70,35,C.accent,true);
 rows(s,[['1. Le client appelle le contrat','Le contrôleur dépend toujours de IReservationService.'],['2. Le décorateur intervient','Il contrôle l’identifiant, puis délègue si la création est admissible.'],['3. Le composant réalise l’opération','ReservationServiceImpl appelle le repository.']],310,650);
 await image(s,'decorator-flow.png','Création de réservation validée ou arrêtée par le décorateur avant le service et la base',790,300,420,236);
}
{
 const s=slide('Les rôles du patron dans AutoLoc','04 · Structure',
 'Lire la correspondance entre les rôles du patron et les classes concrètes. IReservationService est le composant abstrait. ReservationServiceImpl réalise les opérations avec JPA. ReservationServiceDecorator conserve une référence IReservationService et délègue les cinq méthodes. ReservationValidationDecorator étend cette base et redéfinit create. ReservationController représente le client du patron, à distinguer de l’entité métier Client. La base abstraite n’est pas un bean Spring. Seul le décorateur concret doit être injecté au contrôleur.'+source('service/IReservationService.java','service/ReservationServiceImpl.java','service/decorator/ReservationServiceDecorator.java','service/decorator/ReservationValidationDecorator.java','web/controller/ReservationController.java'));
 table(s,[['Rôle du patron','Classe dans AutoLoc'],['Component','IReservationService'],['ConcreteComponent','ReservationServiceImpl'],['Decorator','ReservationServiceDecorator'],['ConcreteDecorator','ReservationValidationDecorator'],['Client du patron','ReservationController']],[360,792],205,400);
}
{
 const s=slide('Pourquoi dans la couche Service ?','05 · Emplacement',
 'Cette règle porte sur la création d’une réservation. La couche Service constitue donc le point où les clients applicatifs doivent la retrouver, indépendamment de HTTP. Un contrôle limité au contrôleur ne protégerait pas un autre appelant du service. Le repository doit rester centré sur l’accès aux données. L’entité Reservation n’a pas besoin de connaître Spring ni cette chaîne de services. Comme le contrôleur dépend déjà d’une interface, la substitution peut être réalisée par injection sans modifier les endpoints. Important : un appel direct au composant interne peut contourner ce contrôle, ce qui impose de consommer le contrat décoré.'+source('web/controller/ReservationController.java','repository/IReservationRepository.java','service/decorator/ReservationValidationDecorator.java'));
 rows(s,[['Frontière métier','La règle concerne create(), indépendamment du protocole HTTP.'],['Point de substitution existant','Le contrôleur utilise déjà IReservationService.'],['Accès JPA préservé','Le composant interne garde les opérations du repository.']]);
 text(s,'Emplacement ajouté : service/decorator/',64,590,1150,40,28,C.accent,true);
}
{
 const s=slide('Le contrat existant reste inchangé','06 · Code réel',
 'Cet extrait reprend exactement la déclaration de l’interface dans le projet. Le Décorateur doit implémenter toutes ces méthodes pour être substituable au service. Notre contrôle spécifique concerne seulement create. Les méthodes findById, findAll, update et deleteById passent par la base délégante sans changer leurs arguments ni leurs retours. Le contrôleur n’a aucune connaissance de la classe concrète injectée.'+source('service/IReservationService.java'));
 code(s,iface.slice(iface.indexOf('public interface')).trim(),205,27);
}
{
 const s=slide('La base abstraite délègue les appels','07 · Code ajouté',
 'Le champ final delegate contient un objet qui respecte IReservationService. Le constructeur fixe le délégué à la construction et chaque méthode transmet son appel. Cet extrait montre le champ, le constructeur et create. Les quatre autres méthodes du même fichier suivent la même structure. Cette base évite de réécrire toute la délégation dans chaque décorateur concret. Elle n’ajoute aucune règle métier et ne porte pas d’annotation Spring. Code complet de la base :\n\n'+base+source('service/decorator/ReservationServiceDecorator.java'));
 code(s,base.slice(base.indexOf('public abstract'),base.indexOf('    @Override\n    public Reservation findById')).trim(),203,24);
 text(s,'Les quatre autres méthodes délèguent aussi, sans transformation.',64,592,1150,42,24,C.accent);
}
{
 const s=slide('Le décorateur contrôle la création','08 · Code ajouté',
 'La méthode reprend le test getId != null et le texte de l’exception à l’identique. Si l’identifiant est renseigné, elle interrompt l’appel avant toute délégation. Sinon, super.create appelle la méthode de la base, qui transmet à delegate.create. Le contrôle existait déjà dans ReservationServiceImpl : il a été déplacé, et non dupliqué. Aucun contrôle de date, aucune assurance ni option payante ne sont ajoutés. Le résultat normal et les exceptions du composant continuent à remonter.'+source('service/decorator/ReservationValidationDecorator.java'));
 code(s,deco.slice(deco.indexOf('    @Override'),deco.lastIndexOf('\n}')).trim(),215,25);
 text(s,'La règle existante s’exécute avant l’appel au service interne.',64,580,1150,50,27,C.accent,true);
}
{
 const s=slide('Le service interne conserve la persistance','09 · Code modifié',
 'Fichier modifié : autoloc-api/src/main/java/tn/esprit/autoloc/autolocapi/service/ReservationServiceImpl.java. Le bean devient @Service("reservationServiceCore") pour que le décorateur puisse désigner le service interne sans ambiguïté. La condition reservation.getId() != null et le IllegalArgumentException avec son message existant ont été retirés de create() et déplacés dans ReservationValidationDecorator.create(). Le test reste identique : c’est un déplacement, pas une nouvelle règle. La sauvegarde reservationRepository.save(reservation) reste ici, car l’accès JPA appartient au service concret. Pourquoi séparer ? Le décorateur porte désormais le contrôle et le service concret la persistance. Les autres méthodes ne changent pas. Le commentaire concernant les dates reste un commentaire, pas une validation exécutée.'+source('service/ReservationServiceImpl.java','service/decorator/ReservationValidationDecorator.java'));
 code(s,core.slice(core.indexOf('@Service'),core.indexOf('    @Override\n    public Reservation findById')).trim(),215,23);
 text(s,'Règle déplacée : create() → ReservationValidationDecorator',64,555,1150,37,23,C.accent,true);
 text(s,'Fichier modifié : service/ReservationServiceImpl.java',64,603,1150,34,21,C.muted);
}
{
 const s=slide('Le branchement avec Spring Boot','10 · Injection',
 'Le code ajouté se trouve dans autoloc-api/src/main/java/tn/esprit/autoloc/autolocapi/service/decorator/. ReservationServiceDecorator implémente IReservationService et délègue les cinq méthodes à delegate. ReservationValidationDecorator étend cette base et redéfinit create(). Les deux classes de production ajoutées sont donc dans le package service/decorator. Le décorateur est dans la couche Service car le contrôle doit s’exécuter pour les appels métier qui passent par IReservationService, pas seulement pour les requêtes HTTP du contrôleur. Le contrôleur garde son interface et n’a pas été modifié. @Service enregistre les deux composants. @Primary choisit le décorateur au point d’injection du contrôleur. @Qualifier("reservationServiceCore") relie le décorateur au ReservationServiceImpl interne. La base abstraite n’est pas un bean. Le constructeur explicite rend cette dépendance claire.'+source('service/decorator/ReservationServiceDecorator.java','service/decorator/ReservationValidationDecorator.java','service/ReservationServiceImpl.java','web/controller/ReservationController.java')+spring);
 code(s,deco.slice(deco.indexOf('@Service'),deco.indexOf('    @Override')).trim(),210,24);
 text(s,'@Primary : le décorateur pour le contrôleur',64,485,1150,42,27,C.accent,true);
 text(s,'@Qualifier : le service interne pour le décorateur',64,546,1150,42,27,C.accent,true);
 text(s,'Emplacement : couche Service, package service/decorator',64,603,1150,36,22,C.muted);
}
{
 const s=slide('Le déroulement d’une requête','11 · Exécution',
 'Le chemin complet est POST /api/reservations : /api vient de application.properties et /reservations du contrôleur. Spring désérialise le corps en Reservation. Le contrôleur appelle l’interface et atteint le décorateur. Si id est null, l’appel atteint ReservationServiceImpl puis le repository. En cas de sauvegarde réussie, le contrôleur répond 201 avec l’objet renvoyé. Si id est renseigné, IllegalArgumentException remonte au GlobalExceptionHandler qui construit une réponse 400 contenant statut, message et horodatage. Il n’y a alors aucun appel au repository. Le cas id null n’affirme pas que toutes les autres contraintes de base seront satisfaites.'+source('web/controller/ReservationController.java','exception/GlobalExceptionHandler.java','service/decorator/ReservationValidationDecorator.java')+'\nSource : autoloc-api/src/main/resources/application.properties');
 text(s,'POST /api/reservations',64,207,1150,45,32,C.accent,true);
 table(s,[['Étape','Identifiant absent','Identifiant fourni'],['Contrôleur','Appelle create(reservation)','Appelle create(reservation)'],['Décorateur','Délègue la création','Lève IllegalArgumentException'],['Service / repository','Sauvegarde via save()','Aucun appel'],['Réponse HTTP','201 si la sauvegarde réussit','400 via GlobalExceptionHandler']],[240,456,456],280,295);
}
{
 const s=slide('Les vérifications du comportement','12 · Tests',
 'Le fichier ReservationValidationDecoratorTests contient cinq tests JUnit. Les mocks vérifient les interactions exactes et l’identité des objets renvoyés. Le test Spring ouvre un AnnotationConfigApplicationContext avec le vrai service, le vrai décorateur, le vrai contrôleur et un repository simulé. Il vérifie le choix du bean principal et le câblage du délégué, sans MySQL. Il appelle directement le contrôleur, donc il ne constitue pas un test HTTP de bout en bout ni un test du gestionnaire d’exceptions. Commande depuis autoloc-api : .\\mvnw.cmd -Dtest=ReservationValidationDecoratorTests test. Le test général contextLoads dépend toujours de la configuration MySQL du projet.\nSource : autoloc-api/src/test/java/tn/esprit/autoloc/autolocapi/service/decorator/ReservationValidationDecoratorTests.java');
 table(s,[['Cas testé','Propriété vérifiée'],['Identifiant renseigné','Même exception, aucun appel au délégué'],['Création admissible','Un appel, même objet renvoyé'],['Quatre autres opérations','Arguments et retours préservés'],['Erreur du service interne','Même exception propagée'],['Contexte Spring minimal','Décorateur injecté, service interne correctement relié']],[395,757],207,390);
}
{
 const s=slide('Les avantages et les limites','13 · Analyse critique',
 'Le bénéfice principal est la séparation de responsabilités. Une nouvelle décoration pourrait être composée sans modifier le contrôleur, ce qui illustre le principe ouvert/fermé. Toutefois, l’extraction initiale exige bien une modification du composant existant. Pour une unique condition, deux classes supplémentaires peuvent sembler disproportionnées : l’objectif académique est ici explicite. Un appel direct à ReservationServiceImpl ou une injection du bean reservationServiceCore contourne la règle. Il faut donc utiliser IReservationService sans qualifier le cœur dans les clients. Enfin, plusieurs décorateurs imposeraient un ordre de composition explicite. Une Strategy changerait un algorithme, un Adapter changerait un contrat, tandis que notre Decorator ajoute un contrôle autour du même contrat.');
 table(s,[['Avantages','Limites'],['Validation isolée et testable','Deux classes supplémentaires pour une règle simple'],['Contrôleur et contrat inchangés','Le service interne peut contourner le contrôle'],['Composition extensible','L’ordre compte si plusieurs décorateurs existent'],['Persistance conservée dans le service','Le patron ne résout pas les validations absentes']],[576,576],218,345);
}
{
 const s=slide('Les fichiers ajoutés et le résultat','14 · Bilan',
 'Bilan précis des modifications et motivation. Fichiers ajoutés : autoloc-api/src/main/java/tn/esprit/autoloc/autolocapi/service/decorator/ReservationServiceDecorator.java, base qui délègue les cinq opérations ; et ReservationValidationDecorator.java, qui reprend le test id non null et l’exception existante dans create(). Fichier modifié : autoloc-api/src/main/java/tn/esprit/autoloc/autolocapi/service/ReservationServiceImpl.java. Le bean reçoit le nom reservationServiceCore et le test y est retiré ; la sauvegarde JPA y reste. Fichier de tests ajouté : autoloc-api/src/test/java/tn/esprit/autoloc/autolocapi/service/decorator/ReservationValidationDecoratorTests.java. Pourquoi la couche Service ? La règle est attachée à la création métier d’une réservation, tandis que le contrôleur s’occupe de HTTP. Décorer IReservationService permet de garder le contrôleur inchangé et d’appliquer la règle au point d’entrée métier habituel. Le repository conserve son rôle de persistance. Un client qui injecte directement reservationServiceCore contournerait cependant le décorateur.'+source('service/decorator/ReservationServiceDecorator.java','service/decorator/ReservationValidationDecorator.java','service/ReservationServiceImpl.java','web/controller/ReservationController.java'),true);
 text(s,'service/decorator/',64,211,1150,38,29,C.light,true);
 text(s,'ReservationServiceDecorator.java\nReservationValidationDecorator.java',64,262,1150,100,29,C.white,false,'Consolas');
 text(s,'service/ReservationServiceImpl.java',64,390,1150,42,25,C.light,false,'Consolas');
 text(s,'Modifié : nom du bean et retrait du contrôle déplacé.',64,448,1150,45,24,C.white);
 text(s,'service/decorator/ReservationValidationDecoratorTests.java',64,500,1150,39,20,C.light,false,'Consolas');
 text(s,'Même contrat public. Même règle. Responsabilités séparées.',64,560,1150,50,29,C.white,true);
}
await fs.mkdir(path.join(build,'previews'),{recursive:true});
await (await PresentationFile.exportPptx(p)).save(path.join(build,'candidate.pptx'));
for(let i=0;i<p.slides.items.length;i++){
 const b=await p.export({slide:p.slides.items[i],format:'png',scale:1});
 await fs.writeFile(path.join(build,'previews',`slide-${i+1}.png`),new Uint8Array(await b.arrayBuffer()));
 console.log('Rendered slide '+(i+1));
}
const {finalizePresentation}=await import(pathToFileURL(path.join(skill,'container_tools/artifact_tool_utils.mjs')).href);
const result=await finalizePresentation({workspaceDir:root,candidatePath:path.join(build,'candidate.pptx'),finalPath:path.join(root,'livrables','AutoLoc_Decorateur_illustre_v3.pptx'),pythonExecutable:'C:/Users/MSI/.cache/codex-runtimes/codex-primary-runtime/dependencies/python/python.exe',integrityValidatorPath:path.join(skill,'container_tools/inspect_presentation_package_integrity.py'),layoutValidatorPath:path.join(skill,'container_tools/inspect_presentation_layout_geometry.py'),layoutArgs:['--expected-slide-size-emu','12192000,6858000','--validate-bullet-geometry','--validate-heading-fit',...[5,12,13,14].flatMap(n=>['--require-native-table-slide',String(n)])],explicitTotalSlideCount:15,requiredNativeTableOwnerSlides:[5,12,13,14],fontPolicy,verifyArtifactToolImport:true,receiptPath:path.join(build,'validation-illustrated-v3.json')});
console.log(JSON.stringify(result));
