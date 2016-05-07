package mgcoders.uy.service.users;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.PreconditionFailedException;
import com.google.gdata.util.ServiceException;
import mgcoders.uy.model.Departamento;
import mgcoders.uy.model.Persona;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by raul on 07/05/16.
 */
public class GoogleContactsService {

    final ContactsService myService;

    public GoogleContactsService() throws AuthenticationException {
        myService = new ContactsService("SampleApp");
        myService.setUserCredentials("email", "password");
    }


    public ContactEntry createContact(Persona persona) throws IOException, ServiceException {
        // Create the entry to insert.
        ContactEntry contact = new ContactEntry();
        // Set the contact's name.
        Name name = new Name();
        final String NO_YOMI = null;
        name.setFullName(new FullName(persona.getNombre(), NO_YOMI));
        contact.setName(name);
        // Set contact's e-mail addresses.
        Email primaryMail = new Email();
        primaryMail.setAddress(persona.getEmail());
        primaryMail.setDisplayName(persona.getNombre());
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);
        // Set contact's phone numbers.
        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber(persona.getTelefono_1());
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);
        PhoneNumber secondaryPhoneNumber = new PhoneNumber();
        secondaryPhoneNumber.setPhoneNumber(persona.getTelefono_2());
        secondaryPhoneNumber.setRel("http://schemas.google.com/g/2005#home");
        contact.addPhoneNumber(secondaryPhoneNumber);

        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactEntry createdContact = myService.insert(postUrl, contact);
        System.out.println("Contact's ID: " + createdContact.getId());
        return createdContact;
    }

    public ContactEntry addGroupMembership(URL contactURL, String groupAtomId)
            throws IOException, ServiceException {
        ContactEntry contact = myService.getEntry(contactURL, ContactEntry.class);
        contact.addGroupMembershipInfo(new GroupMembershipInfo(false, groupAtomId));
        URL editUrl = new URL(contact.getEditLink().getHref());
        try {
            ContactEntry updatedContact = myService.update(editUrl, contact);
            return contact;
        } catch (PreconditionFailedException e) {
            // Etags mismatch: handle the exception.
        }
        return null;
    }

    public ContactGroupEntry createContactGroup(Departamento departamento)
            throws ServiceException, IOException {
        // Create the entry to insert
        ContactGroupEntry newGroup = new ContactGroupEntry();
        newGroup.setTitle(new PlainTextConstruct(departamento.getNombre()));


        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
        ContactGroupEntry createdGroup = myService.insert(postUrl, newGroup);

        System.out.println("Contact group's Atom Id: " + departamento.getNombre());
        return createdGroup;
    }

    public List<ContactGroupEntry> getAllGroups()
            throws ServiceException, IOException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
        ContactGroupFeed resultFeed = myService.getFeed(feedUrl, ContactGroupFeed.class);

        return resultFeed.getEntries();


    }
}
